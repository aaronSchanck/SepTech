from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

PORT = 587


def email(receiver_address: str, msg: MIMEMultipart):
    # Create SMTP session for sending the mail
    session = smtplib.SMTP("smtp.gmail.com", PORT)  # use gmail with port
    session.starttls()  # enable security
    session.login(sender_address, sender_pass)  # login with mail_id and password
    text = msg.as_string()
    session.sendmail(sender_address, receiver_address, text)
    session.quit()