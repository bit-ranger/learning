import datetime
import json


class GenericJSONEncoder(json.JSONEncoder):

    def __retrieve_data(self, obj, cls):

        if isinstance(obj, datetime.datetime):
            return {'timestamp': obj.timestamp()}

        if hasattr(cls, '__json_encode__'):
            return obj.__json_encode__
        else:
            return obj.__dict__

    def default(self,
                obj):
        try:
            return super().default(obj)
        except TypeError:
            pass
        cls = type(obj)
        result = {
            '__custom__': True,
            '__module__': cls.__module__,
            '__name__': cls.__name__,
            'data': self.__retrieve_data(obj, cls)
        }
        return result


class GenericJSONDecoder(json.JSONDecoder):

    def decode(self,
               s,
               _w=json.decoder.WHITESPACE.match):
        result = super().decode(s, _w)

        if not isinstance(result, dict) or not result.get('__custom__', False):
            return result
        return self.__result_to_object(result)

    def __result_to_object(self, result):

        module = result['__module__']
        name = result['__name__']

        if module == 'datetime' and name == 'datetime':
            return datetime.datetime.fromtimestamp(result['data']['timestamp'])

        import sys
        if module not in sys.modules:
            __import__(module)
        cls = getattr(sys.modules[module], result['__name__'])
        instance = cls.__new__(cls)
        if hasattr(cls, '__json_decode__'):
            return cls.__json_decode__(result['data'])

        for k, v in result['data'].items():
            result['data'][k] = v \
                if not isinstance(v, dict) or not v.get('__custom__', False) \
                else self.__result_to_object(v)

        instance.__dict__.update(result['data'])
        return instance


def generic_json_dumps(obj):
    return json.dumps(obj, cls=GenericJSONEncoder)


def generic_json_loads(obj):
    return json.loads(obj, cls=GenericJSONDecoder)