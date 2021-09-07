package TechNinjas.LocaFacil.app.models.enums;

public enum Profile {

    ADMIN(0, "ROLE_ADMIN"), USER(1, "ROLE_USER");

    private Integer cod;
    private String descricao;

    Profile(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Profile toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(Profile x : Profile.values()) {
            if(cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida! " + cod);
    }
}
