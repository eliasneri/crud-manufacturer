package crud.backend.core.utils;

import crud.backend.core.entities.ManufactureEntity;
import crud.backend.dto.ManufactureDTO;
import crud.backend.services.exceptions.ManufactureInvalidCNPJ;

public class Utils {

    private static ManufactureDTO entityToDTO(ManufactureEntity entity) {
        return new ManufactureDTO(entity);
    }

    private static ManufactureEntity toEntity (ManufactureDTO dto) {
        ManufactureEntity entity = new ManufactureEntity();
        entity.setManufactureId(dto.getManufactureId());
        entity.setManufacturerActive(dto.isManufacturerActive());
        entity.setManufactureNeighbourhood(dto.getManufactureNeighbourhood());
        return entity;
    }

    public static boolean validateDto(ManufactureDTO dto) {
        boolean ok = false;
        if (dto.getManufacturerCNPJ().isEmpty() || dto.getManufacturerCNPJ().isBlank() || dto.getManufacturerCNPJ() == null){
            ok = true;
            throw new ManufactureInvalidCNPJ("CNPN is empty!");
        }

        int i = dto.getManufacturerCNPJ().length();
        if (dto.getManufacturerCNPJ().length() != 14 && ok)
            throw new ManufactureInvalidCNPJ("CNPN is invalid");


    return true;

    }

    public static String unMaskCNPJ(String cnpj){
        return cnpj.replaceAll("[^0-9]", "");
    }

    public static String maskedCNPJ(String cnpj) {
        String maskedCNPJ = "";
        try {
            cnpj = unMaskCNPJ(cnpj);
            if (cnpj != null && cnpj.trim().length() == 14) {
                maskedCNPJ += cnpj.substring(0, 2);
                maskedCNPJ += ".";
                maskedCNPJ += cnpj.substring(2, 5);
                maskedCNPJ += ".";
                maskedCNPJ += cnpj.substring(5, 8);
                maskedCNPJ += "/";
                maskedCNPJ += cnpj.substring(8, 12);
                maskedCNPJ += "-";
                maskedCNPJ += cnpj.substring(12, 14);
            } else {
                maskedCNPJ = cnpj + " : invalid!";
            }
        } catch (Exception e) {
            throw new ManufactureInvalidCNPJ("Error on Masked CNPJ!");
        }
        return maskedCNPJ;
    }
}
