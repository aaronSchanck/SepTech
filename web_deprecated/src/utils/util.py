from requests import Response
import json

def respond(_response, *, status_code):
    return Response(
        mimetype="application/json",
        response=json.dumps(_response),
        status=status_code
    )