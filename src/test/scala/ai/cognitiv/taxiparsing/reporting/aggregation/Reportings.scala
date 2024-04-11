package ai.cognitiv.taxiparsing.reporting.aggregation

import ai.cognitiv.taxiparsing.reading.AnalyzedTripRecord
import ai.cognitiv.taxiparsing.reading.conversion.TaxiColor

import java.time.LocalDateTime

trait Reportings {

    val sampleGreenAnalyzedTripRecord = AnalyzedTripRecord.builder()
        .vendorId(1L)
        .pickUpDateTime(LocalDateTime.parse("2023-10-01T00:00:03.616"))
        .dropOffDateTime(LocalDateTime.parse("2023-10-01T00:00:01.616"))
        .fareAmount(1)
        .tollAmount(2)
        .paymentType(1)
        .pickUpLocationId(23)
        .dropOffLocationId(42)
        .color(TaxiColor.GREEN)
        .build()

}
