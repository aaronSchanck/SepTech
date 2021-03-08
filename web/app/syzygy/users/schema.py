from marshmallow import fields, Schema


class UserSchema(Schema):
    userid = fields.Number(attribute="uuid")
    email = fields.Email()
    username = fields.Str()
    password = fields.Str()
    first_name = fields.Str()
    last_name = fields.Str()
    date_of_birth = fields.DateTime()
    
    # created_at = fields.DateTime()
    # modified_at = fields.DateTime()