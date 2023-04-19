package com.tm.transfer.application.exceptions

import java.lang.RuntimeException

class InvalidValueFormatException(exception: String?): RuntimeException(exception)
