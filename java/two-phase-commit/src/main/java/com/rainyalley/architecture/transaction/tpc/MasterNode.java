package com.rainyalley.architecture.transaction.tpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class MasterNode{

    private List<Node> slaves;

    private static final ExecutorService es = Executors.newCachedThreadPool();

    private List<Node> voteAllowSlaves;

    /**
     * 状态
     * 0 已prepare
     * 1 已commit
     * 2 已abort
     */
    private Status status = Status.none;


    public boolean prepare(){

        List<Future<VoteResp>> fl = new ArrayList<Future<VoteResp>>();

        for (final Node slave : slaves) {
            Future<VoteResp> future = es.submit(new VoteReqTask(slave, this, slaves));
            fl.add(future);
        }

        voteAllowSlaves = new ArrayList<Node>();
        for (Future<VoteResp> future : fl) {
            try {
                VoteResp resp = future.get();
                if(resp.isAllow()){
                    voteAllowSlaves.add(resp.getNode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //没有全部同意
        if(voteAllowSlaves.size() != slaves.size()){
            System.out.println("[master] prepare failed");
            return false;
        }

        System.out.println("[master] prepare success");
        return true;
    }

    public void commit(){
        if(Status.prepare.equals(status)){
            throw new IllegalStateException("can not commit");
        }

        for (final Node slave : slaves) {
            es.submit(new CommitTask(slave));
        }

        status = Status.commit;

    }

    public void abort(){

        for (final Node slave : slaves) {
            es.submit(new AbortTask(slave));
        }

        status = Status.abort;
    }


    public void setSlaves(List<Node> slaves) {
        this.slaves = slaves;
    }


    public Status getStatus() {
        return status;
    }

    private static class VoteReqTask implements Callable<VoteResp> {

        private MasterNode master;

        private List<Node> slaves;

        private Node node;

        public VoteReqTask(Node node, MasterNode master, List<Node> slaves) {
            this.master = master;
            this.slaves = slaves;
            this.node = node;
        }

        @Override
        public VoteResp call() throws Exception {

            Future<VoteResp> f = es.submit(new Callable<VoteResp>() {
                @Override
                public VoteResp call() throws Exception {
                    //Thread.sleep(3000);
                    return node.prepare(new VoteReq(master, slaves));
                }
            });

            try {
                VoteResp resp =  f.get(Config.timeOut, Config.timeUnit);
                System.out.println("[master] VoteReqTask-[" + node.getName() +"] prepare " + resp.isAllow());
                return resp;
            } catch (TimeoutException e) {
                VoteResp resp = new VoteResp(false, null);
                System.out.println("[master] VoteReqTask-[" + node.getName() +"] prepare timeout");
                return resp;
            }
        }
    }

    private static class AbortTask implements Callable<Boolean> {

        private Node node;

        public AbortTask(Node node) {
            this.node = node;
        }

        @Override
        public Boolean call() throws Exception {

            Future<Boolean> f = es.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // ~TODO~: 2016/8/3
                    Thread.sleep(4000);
                    return node.abort();
                }
            });

            try {
                Boolean resp =  f.get(Config.timeOut, Config.timeUnit);
                System.out.println("[master] AbortTask[" + node.getName() +"] abort " + resp);
                return resp;
            } catch (TimeoutException e) {
                Boolean resp = false;
                System.out.println("[master] AbortTask[" + node.getName() +"] abort timeout");
                return resp;
            }
        }
    }

    private static class CommitTask implements Callable<Boolean> {

        private Node node;

        public CommitTask(Node node) {
            this.node = node;
        }

        @Override
        public Boolean call() throws Exception {

            Future<Boolean> f = es.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    // ~TODO~: 2016/8/3
                    Thread.sleep(4000);
                    return node.commit();
                }
            });

            try {
                Boolean resp =  f.get(Config.timeOut, Config.timeUnit);
                System.out.println("[master] CommitTask[" + node.getName() +"] commit " + resp);
                return resp;
            } catch (TimeoutException e) {
                Boolean resp = false;
                System.out.println("[master] CommitTask[" + node.getName() +"] commit timeout");
                return resp;
            }
        }
    }

    public enum Status{
        none,prepare,commit,abort
    }
}
