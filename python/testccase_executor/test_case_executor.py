from typing import List, Dict


class SystemInfo:

    _conf = ...  # type: Dict

    pass


class TaskInfo:

    __id = ...  # type: str
    __conf = ...  # type: Dict
    __system_info = ...  # type: SystemInfo
    __global_var = ...  # type: Dict

    pass


class JobInfo:

    __id = ...  # type: str
    __conf = ...  # type: Dict
    __task_info = ...  # type: TaskInfo

    pass


class TestCaseInfo:

    __id = ...  # type: str
    __data = ...  # type: Dict

    pass


class TestPointResult:

    pass


class TestCaseResult:

    __id = ...  # type: str
    __test_point_result_list = ...  # type: List[TestPointResult]

    pass


# tc簇
class TestCaseCluster:

    __job_info = ...  # type: JobInfo
    __conf = ...  # type: Dict
    __data_list = ...  # type: List[TestCaseInfo]

    pass


class TestCaseClusterResult:

    __test_case_cluster = ...  # type: TestCaseCluster
    __test_case_result_list = ...  # type: List[TestCaseResult]

    pass


class TestCaseExecutor:

    def execute(self, test_case_cluster: TestCaseCluster):
        pass


class CeleryTestCaseExecutor(TestCaseExecutor):

    def execute(self, test_case_cluster: TestCaseCluster):
        pass


class ThreadPoolTestCaseExecutor(TestCaseExecutor):

    def execute(self, test_case_cluster: TestCaseCluster):
        pass


def test_case_task(self, test_case_cluster: TestCaseCluster) -> TestCaseClusterResult:
    pass


def test_case_complete_callback(test_case_cluster_result_list: List[TestCaseClusterResult]):
    pass
