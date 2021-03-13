import contextlib
import logging
from logging.handlers import RotatingFileHandler


class DebugFilter(logging.Filter):
    def filter(self, rec):
        return rec.levelno == logging.DEBUG


@contextlib.contextmanager
def setup_logging():
    try:
        # __enter__
        max_bytes = 32 * 1024 * 1024  # 32 MiB
        logging.getLogger("centauri").setLevel(logging.INFO)
        logging.getLogger("centauri-debug").setLevel(logging.DEBUG)

        log = logging.getLogger()
        log.setLevel(logging.DEBUG)
        handler = RotatingFileHandler(
            filename="centauri.log",
            encoding="utf-8",
            mode="w",
            maxBytes=max_bytes,
            backupCount=5,
        )

        handler2 = RotatingFileHandler(
            filename="centauri-debug.log",
            encoding="utf-8",
            mode="w",
            maxBytes=max_bytes,
            backupCount=5,
        )
        dt_fmt = "%Y-%m-%d %H:%M:%S"
        fmt = logging.Formatter(
            "[{asctime}] [{levelname:<7}] {name}: {message}", dt_fmt, style="{"
        )
        handler.setFormatter(fmt)
        handler2.setFormatter(fmt)

        handler2.addFilter(DebugFilter())

        log.addHandler(handler)
        log.addHandler(handler2)

        yield
    finally:
        # __exit__
        handlers = log.handlers[:]
        for hdlr in handlers:
            hdlr.close()
            log.removeHandler(hdlr)
