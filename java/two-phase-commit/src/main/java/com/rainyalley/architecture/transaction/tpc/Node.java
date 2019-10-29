package com.rainyalley.architecture.transaction.tpc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;

public class Node {

    private static final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    private static final ExecutorService es = Executors.newCachedThreadPool();

    private String name;

    private VoteReq req;

    //0 已投票
    //1 已commit
    //2 已abort
    private Integer status = -1;

    public Node(String name) {
        this.name = name;
    }

    public VoteResp prepare(VoteReq req){
        this.req = req;
        status = 0;
        try {

            if(name.equals("slave-1")){
                throw new RuntimeException();
            }

            //3秒后询问其它节点
            ses.schedule(new Runnable() {
                @Override
                public void run() {
                    cooperative();
                }
            }, Config.timeOut, Config.timeUnit);


            return new VoteResp(true, this);
        } catch (Exception e) {
            abort();
            return new VoteResp(false, this);
        }


    }

    public Boolean commit(){
        synchronized (status){
            if(status == 0){
                System.out.println("[" + name + "] commit");
            } else if(status == 2){
                System.err.println("[" + name + "] invalid status " + status);
            }

            status = 1;
            return true;
        }
    }

    public Boolean abort(){
        synchronized (status){
            if(status == 0){
                System.out.println("[" + name + "] abort");
            } else if(status == 1){
                System.err.println("[" + name + "] invalid status " + status);
            }

            status = 2;
            return true;
        }

    }

    public String getName() {
        return this.name;
    }


    private void cooperative(){
        MasterNode.Status status = null;
        try {
            status = es.submit(new CooperativeTask(req.getMasterNode(), this)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(MasterNode.Status.commit.equals(status)){
            commit();
        } else if(MasterNode.Status.abort.equals(status)){
            abort();
        } else {
            System.err.println("cooperative, MasterNode in a invalid status");
        }
    }


    private static class CooperativeTask implements Callable<MasterNode.Status> {

        private MasterNode masterNode;
        private Node node;


        public CooperativeTask(MasterNode masterNode, Node node) {
            this.masterNode = masterNode;
            this.node = node;
        }

        @Override
        public MasterNode.Status call() throws Exception {

            Future<MasterNode.Status> f = es.submit(new Callable<MasterNode.Status>() {
                @Override
                public MasterNode.Status call() throws Exception {
                    return masterNode.getStatus();
                }
            });

            try {
                MasterNode.Status resp =  f.get(Config.timeOut, Config.timeUnit);
                System.out.println("[" + node.getName() +  "] CooperativeTask status " + resp);
                return resp;
            } catch (TimeoutException e) {
                System.out.println("[" + node.getName() +  "] CooperativeTask status timeout");

                // ~TODO~: 2016/8/3 retry

                return MasterNode.Status.abort;
            }
        }
    }
}
