package crud.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import crud.backend.core.entities.ManufactureEntity;
import crud.backend.core.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureDTO {


    private Long manufactureId;
    private String manufacturerName;
    private String manufacturerCNPJ;
    private String manufacturerFantasyName;
    private String manufacturerSocialName;
    private boolean manufacturerActive;
    private String manufacturerSite;
    private String manufacturerCountry;
    private String manufacturerCity;
    private String manufactureNeighbourhood;

    public ManufactureDTO(ManufactureEntity entity){
        manufactureId=entity.getManufactureId();
        manufacturerName=entity.getManufacturerName();
        manufacturerCNPJ= Utils.maskedCNPJ(entity.getManufacturerCNPJ());
        manufacturerFantasyName=entity.getManufacturerFantasyName();
        manufacturerSocialName=entity.getManufacturerSocialName();
        manufacturerActive=entity.isManufacturerActive();
        manufacturerSite=entity.getManufacturerSite();
        manufacturerCountry=entity.getManufacturerCountry();
        manufacturerCity=entity.getManufacturerCity();
        manufactureNeighbourhood = entity.getManufactureNeighbourhood();

    }
}
