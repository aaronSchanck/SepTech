import bcrypt


def encrypt_pw(password):
    salt = bcrypt.gensalt(16)

    hashed = bcrypt.hashpw(password.encode("utf-8"), salt)

    return hashed