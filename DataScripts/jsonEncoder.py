import json


class JsonEncoder(json.JSONEncoder):
    def default(self, obj):
        return obj.__dict__