import json
from typing import Dict, Any

from datetime import datetime


class Record:

    def __init__(self,
                 job_id, job_name):
        self.job_id = job_id.strip()
        self.job_name = job_name.strip()
        self.project_name = None
        self.state = None
        self.start_time = None
        self.end_time = None
        self.failed_step_name = None
        self.unit_test = None
        self.case_test = None
        self.code_check = None

records = dict()  # type: Dict[str, Record]

success_record_non_match = []
failure_record_non_match = []
success_value_record_non_match = []

def key_of_record(r):
    return r.job_id.strip() + '_' + r.job_name.strip()

def record(l):
    c = l.split(',')
    r = Record(c[1].strip(), c[2].strip())
    r.project_name = c[3].strip()
    r.state = c[4].strip()
    return r

def success_record(l):
    c = l.split(',')
    r = Record(c[1].strip(), c[2].strip())
    k = key_of_record(r)
    if k not in records:
        success_record_non_match.append(k)
        return None
    r = records.get(k, r)
    r.start_time = c[5].strip()
    r.end_time = c[6].strip()
    return r

def failure_record(l):
    c = l.split(',')
    r = Record(c[7].strip(), c[9].strip())
    k = key_of_record(r)
    if k not in records:
        failure_record_non_match.append(k)
        return None
    r = records.get(k, r)
    r.failed_step_name = c[2].strip()
    return r

def blank_to_zero(txt):
    if len(txt.strip()) == 0:
        return 0
    else:
        return int(txt.strip())

def success_value_record(l):
    c = l.split(',')
    r = Record(c[1].strip(), c[2].strip())
    k = key_of_record(r)
    if k not in records:
        success_value_record_non_match.append(k)
        return None
    r = records.get(k, r)
    r.unit_test = blank_to_zero(c[5])
    r.case_test = blank_to_zero(c[6])
    r.code_check = blank_to_zero(c[7])
    return r

def read_file(file_name, func):
    with open('D:\\OneDrive\\文档\\汇付天下\\流水线统计\\' + file_name, encoding='gb2312') as f:
        lines = f.readlines()
        dul_key_list = []
        for item in filter(lambda x: x is not None, map(func, lines[1:])):
            key = key_of_record(item)
            if key in dul_key_list:
                dul_key_list.append(key)
            else:
                records[key] = item
        print('read %s lines from %s, ignore %s lines' % (len(lines), file_name, len(dul_key_list)))


read_file('30天流水.csv', record)
read_file('30天成功.csv', success_record)
read_file('30天失败.csv', failure_record)
read_file('30天成功数据.csv', success_value_record)

print('success_record_non_match %s' % (len(success_record_non_match)))
print('failure_record_non_match %s' % (len(failure_record_non_match)))
print('success_value_record_non_match %s' % (len(success_value_record_non_match)))

print('total record %s' % (len(records)))


class Analysis:

    def __init__(self, project_name):
        self.project_name = project_name
        self.success = 0
        self.failure = 0
        self.aborted = 0
        self.mr_success = 0
        self.mr_failure = 0

        self.cost_time_max = 0
        self.cost_time_min = 0
        self.cost_time_new = 0
        self.cost_time_ava = 0
        self.cost_time_total = 0

        self.unit_test_max = 0
        self.unit_test_min = 0
        self.unit_test_new = 0
        self.unit_test_ava = 0
        self.unit_test_total = 0

        self.case_test_max = 0
        self.case_test_min = 0
        self.case_test_new = 0
        self.case_test_ava = 0
        self.case_test_total = 0

        self.code_check_max = 0
        self.code_check_min = 0
        self.code_check_new = 0
        self.code_check_ava = 0
        self.code_check_total = 0

        self.failure_step_cnt = {}

    def add_record(self, r: Record):
        if r.state == 'Success':
            self.success += 1
            self.cost_time(r)
            self.unit_test(r)
            self.case_test(r)
            self.code_check(r)
        elif r.state == 'Failed':
            self.failure += 1
            fsnn = self.failure_step_cnt.get(r.failed_step_name, 0)
            self.failure_step_cnt[r.failed_step_name] = fsnn+1
        elif r.state == 'Aborted':
            self.aborted += 1
        else:
            raise ValueError(r.state)

    def cost_time(self, r):
        fmt = '%Y/%m/%d %H:%M:%S'
        rct = (datetime.strptime(r.end_time, fmt) - datetime.strptime(r.start_time, fmt)).total_seconds()
        self.cost_time_new = rct
        self.cost_time_total += rct
        self.cost_time_ava = self.cost_time_total / self.success
        if rct > self.cost_time_max:
            self.cost_time_max = rct
        if self.cost_time_min == 0:
            self.cost_time_min = rct
        elif rct < self.cost_time_min:
            self.cost_time_min = rct

    def unit_test(self, r):
        if r.unit_test is None or r.unit_test == 0:
            return
        self.unit_test_new = r.unit_test
        self.unit_test_total += r.unit_test
        self.unit_test_ava = self.unit_test_total / self.success
        if r.unit_test > self.unit_test_max:
            self.unit_test_max = r.unit_test
        if self.unit_test_min == 0:
            self.unit_test_min = r.unit_test
        elif r.unit_test < self.unit_test_min:
            self.unit_test_min = r.unit_test

    def case_test(self, r):
        if r.case_test is None or r.unit_test == 0:
            return
        self.case_test_new = r.case_test
        self.case_test_total += r.case_test
        self.case_test_ava = self.case_test_total / self.success
        if r.case_test > self.case_test_max:
            self.case_test_max = r.case_test
        if self.case_test_min == 0:
            self.case_test_min = r.case_test
        elif r.case_test < self.case_test_min:
            self.case_test_min = r.case_test
            
    def code_check(self, r):
        if r.code_check is None or r.code_check == 0:
            return
        self.code_check_new = r.code_check
        self.code_check_total += r.code_check
        self.code_check_ava = self.code_check_total / self.success
        if r.code_check > self.code_check_max:
            self.code_check_max = r.code_check
        if self.code_check_min == 0:
            self.code_check_min = r.code_check
        elif r.code_check < self.code_check_min:
            self.code_check_min = r.code_check

    def __str__(self):
        d_str = ''
        for k,v in self.failure_step_cnt.items():
            d_str+=(k + ':' + '%.2f' % (v/self.failure) + ';')

        cols = [
            self.project_name,
            self.success,
            self.failure,
            self.aborted,
            self.mr_success,
            self.mr_failure,

            self.cost_time_max,
            self.cost_time_min,
            self.cost_time_new,
            self.cost_time_ava,
            self.cost_time_total,

            self.unit_test_max,
            self.unit_test_min,
            self.unit_test_new,
            self.unit_test_ava,

            self.case_test_max,
            self.case_test_min,
            self.case_test_new,
            self.case_test_ava,
            self.case_test_total,

            self.code_check_max,
            self.code_check_min,
            self.code_check_new,
            self.code_check_ava,
            self.code_check_total,
            d_str
        ]


        return ','.join(map(str, cols))


analysis = dict()

for k, v in records.items():
    pro_name = v.job_name[:3]
    ana = analysis.get(pro_name, Analysis(pro_name))
    ana.add_record(v)
    analysis[pro_name] = ana

def write_file():
    with open('D:\\OneDrive\\文档\\汇付天下\\流水线统计\\统计.csv', mode='w', encoding='gb2312') as f:
        header = ','.join([
            '项目名称',
            '成功次数',
            '失败次数',
            '中断次数',
            'mr成功次数',
            'mr失败次数',

            '最大耗时',
            '最小耗时',
            '最新耗时',
            '平均耗时',
            '总耗时',

            '最大单元测试覆盖率',
            '最小单元测试覆盖率',
            '最新单元测试覆盖率',
            '平均单元测试覆盖率',

            '最大用例测试数量',
            '最小用例测试数量',
            '最新用例测试数量',
            '平均用例测试数量',
            '总用例测试数量',

            '最大代码检查问题数量',
            '最小代码检查问题数量',
            '最新代码检查问题数量',
            '平均代码检查问题数量',
            '总代码检查问题数量',
            '失败步骤占比'
        ])
        f.write(header)
        f.write('\n')
        for row in map(str, analysis.values()):
            f.write(row)
            f.write('\n')


write_file()


