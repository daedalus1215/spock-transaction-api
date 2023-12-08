package com.kinandcarta.transactionsapi.utils

import com.kinandcarta.transactionsapi.util.DateUtils
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

import java.time.LocalDate

import static java.time.temporal.ChronoField.EPOCH_DAY

@Title("Unit test for Date Utils")
@Narrative("Used for various date-related functions, like converting timestamps into dates of particular formats")
class DateUtilsSpec extends Specification {

    @Unroll
    def "should convert timestamp into date"() {
        given: "a timestamp and a format"
        final def timestamp = LocalDate.of(2023, 12, 10).getLong(EPOCH_DAY)

        expect: "to return the correct date [#expected] in the specified format [#format]"
        final def actual = DateUtils.formatEpochDateAsString(timestamp, format)

        actual == expected

        where:
        format       | expected
        'yyyy-MM-dd' | "2023-12-10"
        'MM-dd-yyyy' | "12-10-2023"
    }
}