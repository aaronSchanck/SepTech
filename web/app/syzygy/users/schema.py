from marshmallow import fields, Schema


class UserSchema(Schema):
    userid = fields.Number(dump_only=True, data_key="uuid")
    email = fields.Email(required=True)
    username = fields.Str(required=True)
    password = fields.Str(required=True, load_only=True)
    first_name = fields.Str(required=True)
    last_name = fields.Str(required=True)
    date_of_birth = fields.DateTime()
    
    # created_at = fields.DateTime()
    # modified_at = fields.DateTime()