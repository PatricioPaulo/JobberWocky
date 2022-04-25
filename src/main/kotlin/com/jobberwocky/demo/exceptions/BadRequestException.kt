package com.jobberwocky.demo.exceptions

class BadRequestException (description: String?) : ApiException("bad_request", description!!, 400)