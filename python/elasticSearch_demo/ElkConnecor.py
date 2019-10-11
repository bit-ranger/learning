from elasticsearch import Elasticsearch
from flask import json

es = Elasticsearch(
    ['172.31.13.43'],
    http_auth=('elastic', 'elastic'),
    scheme="http",
    port=9200,
    retry_on_timeout=True
)



# doc = {
#     'author': 'kimchy',
#     'text': 'Elasticsearch: cool. bonsai cool.',
#     'timestamp': datetime.now(),
# }
# res = es.index(index="pnr-muser-*", doc_type='tweet', id=1, body=doc)
# print(res['result'])

# res = es.get(index="test-index", doc_type='tweet', id=1)
# print(res['_source'])
#
# es.indices.refresh(index="test-index")
#

# exists = es.exists(index="pnr-muser-*", body={"query": {"match_all": {}}})
# print("exists %d:" % exists['exists'])

#
# mget = es.mget(index="pnr-muser-*", body={"query": {"match_all": {}}})
# print("mget %d Hits:" % mget['hits']['total'])
# for hit in mget['hits']['hits']:
#     print("%(@timestamp)s %(source)s %(message)s" % hit["_source"])

ping = es.ping()
if not ping:
    raise RuntimeError("ping ".join(ping))







# msearch = es.msearch(body='{"query" : {"match_all" : {}}, "from" : 0, "size" : 10}'
#                           '\n{"query" : {"match_all" : {}}, "from" : 0, "size" : 10}',
#                      index="pnr-muser-*")
# print("msearch %d Hits:" % msearch["responses"][0]['hits']['total'])
# for hit in msearch["responses"][0]['hits']['hits']:
#     print("%(@timestamp)s %(source)s %(message)s" % hit["_source"])


def count():
    count = es.count(index="pnr-muser-*", body={"query": {"match_all": {}}})
    print("count %d:" % count['count'])


def search_match(index, time_start, time_end, size, match_message):
    """
    :param index: 索引名
    :param time_start: 开始时间
    :param time_end: 结束时间
    :param size: 日志条数
    :param match_message: 关键字，用空格断开
    :return:
    """
    body = {
        "query": {
            "bool": {
                "must": [
                    {"range": {
                        "@timestamp": {
                            "gte": time_start,
                            "lte": time_end,
                        }
                    }},
                    {"match": {
                        "message": {
                            "query": match_message,
                            "operator": "and",
                            "zero_terms_query": "all"
                        }
                    }}
                ],
                "must_not": [
                    {"term": {
                        "source": "/app/muser/logs/biz/biz-default.log"
                    }}
                ]
            }
        },
        "size": size,
        "timeout": "5s",
    }

    search = es.search(index=index, body=body)
    print(json.dumps(search))
    print("search %d Hits:" % search['hits']['total'])
    for hit in search['hits']['hits']:
        print("%(loglevel)s %(@timestamp)s %(message)s" % hit["_source"])


def search_regex():
    body = {
        "query": {
            "bool": {
                "must": [
                    {"regexp": {
                        "message": {
                            "value": "商户代取.*",
                            "flags": "INTERSECTION|COMPLEMENT|EMPTY"
                        }
                    }},
                    {"term": {
                        "loglevel": "INFO"
                    }},
                    {"range": {
                        "@timestamp": {
                            "gte": "2018-05-19T06:00:29",
                            "lte": "2018-05-19T06:00:29",
                        }
                    }}
                ],
                "must_not": [
                    {"term": {
                        "source": "/app/muser/logs/biz/biz-default.log"
                    }}
                ]
            }
        },
        "size": 100,
        "timeout": "5s",
    }
    search = es.search(index="pnr-muser-*", body=body)
    print("search %d Hits:" % search['hits']['total'])
    for hit in search['hits']['hits']:
        print("%(loglevel)s %(@timestamp)s %(message)s" % hit["_source"])


search_match("pnr-muser-2018.06.12", "2018-06-12T06:27:00", "2018-06-12T06:28:00", 100, "")
# search_regex()
