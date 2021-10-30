package TechNinjas.LocaFacil.app.models.enums;

public enum Status {
    AVAILABLE(1), BUSY(2), WAITING(3), RELEASE(4);

    private Integer cod;

    Status(Integer cod){
        this.cod = cod;
    }

    public Integer getCod() {
        return cod;
    }

    public static Status toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(Status x : Status.values()) {
            if(cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid Priority! " + cod);
    }
}
