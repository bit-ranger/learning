from celery import Celery, Task
from celery.utils.log import get_task_logger

logger = get_task_logger(__name__)


app = Celery('tasks')
app.config_from_object('celeryconfig')


class DemoTask(Task):

    def on_success(self, retval, task_id, args, kwargs):   # 任务成功执行
        logger.info('>>>>>>>>>>>>>>>>> task id:{} , arg:{} , successful !'.format(task_id,args))

    def on_failure(self, exc, task_id, args, kwargs, einfo):  #任务失败执行
        logger.info('>>>>>>>>>>>>>>>>> task id:{} , arg:{} , failed ! erros : {}' .format(task_id,args,exc))

    def on_retry(self, exc, task_id, args, kwargs, einfo):    #任务重试执行
        logger.info('>>>>>>>>>>>>>>>>> task id:{} , arg:{} , retry !  einfo: {}'.format(task_id, args, exc))


@app.task(name='tasks.add',
          base=DemoTask,
          # track_started=True,
          # acks_late=True,
          bind=True,
          time_limit=600,
          autoretry_for=(Exception,),
          retry_backoff=True,
          retry_backoff_max=60,
          retry_jitter=True,
          default_retry_delay=60,
          retry_kwargs={
              'max_retries': 3
          })
def add(self, x, y, on_success=None):
    try:
        logger.info('add {0} + {1}, {2}, {3}'.format(x, y, on_success, self.request))
        if x == 3:
            raise Exception("x equal 3!")
        return x + y
    except Exception as e:
        raise self.retry(exec=e)


@app.task(name='tasks.callback')
def callback(result):
    print('>>>>>>>>>>>>>>>>> callback {0}'.format(result))
    return result


@app.task(name='tasks.on_chord_error')
def on_chord_error(request, exc, traceback):
    print('>>>>>>>>>>>>>>>>> Task {0!r} raised error: {1!r}'.format(request.id, exc))
