package com.example.ticketservice.model.helper;

import java.util.Map;

public enum Type {
    /* для пошуку квитків за назвою та типом */
    PLANE_TICKET, TRAIN_TICKET, BUS_TICKET;
    private static Map<Long,Type> typeHashMapLong = Map.of(0L, Type.BUS_TICKET,
            1L, Type.PLANE_TICKET,2L,Type.TRAIN_TICKET);

    private static Map<String,Type> typeHashMapString = Map.of("BUS_TICKET", Type.BUS_TICKET,
            "PLANE_TICKET", Type.PLANE_TICKET, "TRAIN_TICKET", Type.TRAIN_TICKET);

    public static Type fromString(String string){
        return typeHashMapString.get(string);
    }
    public static Type fromLong(Long number){
        return typeHashMapLong.get(number);
    }
}
