BROKER_URL = 'redis://:F4GtbZ8h5GtpZ6LqUCZndW2IzkjA3WWn@redis-17315.c60.us-west-1-2.ec2.cloud.redislabs.com:17315/0'
CELERY_RESULT_BACKEND = 'redis://:F4GtbZ8h5GtpZ6LqUCZndW2IzkjA3WWn@redis-17315.c60.us-west-1-2.ec2.cloud.redislabs.com:17315/0'

CELERY_EVENT_QUEUE_PREFIX = 'learning-python'
CELERY_EVENT_SERIALIZER = 'json'
CELERY_TASK_SERIALIZER = 'json'
CELERY_RESULT_SERIALIZER = 'json'
CELERY_ACCEPT_CONTENT=['json']
CELERY_TIMEZONE = 'Europe/Oslo'
CELERY_ENABLE_UTC = True

CELERY_TASK_ACKS_LATE = True
CELERY_TRACK_STARTED = True

CELERY_ANNOTATIONS = {
    'tasks.add': {
        'rate_limit': '99999999/m'

    }
}

CELERY_ROUTES = {
    'tasks.add': 'learning-python.celery.tasks',
    'tasks.callback': 'learning-python.celery.tasks',
    'tasks.on_chord_error': 'learning-python.celery.tasks'
}