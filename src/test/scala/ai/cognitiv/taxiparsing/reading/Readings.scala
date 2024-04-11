package ai.cognitiv.taxiparsing.reading

import ai.cognitiv.taxiparsing.reading.conversion.{GreenTripRecord, YellowTripRecord}

import java.time.LocalDateTime


trait Readings {

    def yellowSample(): YellowTripRecord = YellowTripRecord.builder()
        .vendorID(1L)
        .tpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:01.004"))
        .tpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:01.009"))
        .passengerCount(1D)
        .tripDistance(0D)
        .rateCodeId(1D)
        .storeAndFwdFlag("N")
        .pickUpLocationId(168L)
        .dropOffLocationId(168L)
        .paymentType(2L)
        .fareAmount(3D)
        .extra(1D)
        .mtaTax(0.5)
        .tipAmount(0D)
        .tollsAmount(0D)
        .improvementSurcharge(1D)
        .totalAmount(5.5)
        .congestionSurcharge(0D)
        .airportFee(0D)
        .build()

    def greenSample(): GreenTripRecord = GreenTripRecord.builder()
        .vendorId(2L)
        .lpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:03.453"))
        .lpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:04.078"))
        .storeAndFwdFlag("N")
        .ratecodeID(1D)
        .pickUpLocationId(166L)
        .dropOffLocationId(74L)
        .passengerCount(1D)
        .tripDistance(1.45)
        .fareAmount(12.1)
        .extra(1.0)
        .mtaTax(0.5)
        .tipAmount(2.92)
        .tollsAmount(0D)
        .improvementSurcharge(1D)
        .totalAmount(17.52)
        .paymentType(1D)
        .tripType(1D)
        .build()

    def sampleYellowData: List[YellowTripRecord] = List(
        yellowSample(),
        yellowSample().toBuilder.tpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:01.404")).tpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:01.427")).build(),
        yellowSample().toBuilder.tpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:01.278")).tpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:01.651")).tripDistance(0.9).pickUpLocationId(161L).dropOffLocationId(186L).paymentType(1L).fareAmount(6.5).extra(3.5).tipAmount(2.9).totalAmount(14.4).congestionSurcharge(2.5).build()
    )

    def sampleGreenData: List[GreenTripRecord] = List(
        greenSample(),
        greenSample().toBuilder.lpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:03.616")).lpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:03.973")).pickUpLocationId(74L).dropOffLocationId(42L).tripDistance(0.89).fareAmount(7.9).tipAmount(0D).totalAmount(10.4).paymentType(2D).build(),
        greenSample().toBuilder.lpepPickUpDatetime(LocalDateTime.parse("2023-10-01T00:00:03.112")).lpepDropOffDatetime(LocalDateTime.parse("2023-10-01T00:00:03.632")).pickUpLocationId(83L).dropOffLocationId(129L).tripDistance(2.38).fareAmount(13.5).tipAmount(0D).totalAmount(16.0).paymentType(2D).build()
    )

}
