package crud.backend.services;

import crud.backend.core.entities.ManufactureEntity;
import crud.backend.core.repositories.ManufactureRepository;
import crud.backend.core.utils.Utils;
import crud.backend.dto.ManufactureDTO;
import crud.backend.interfaces.ManufactureBussiness;
import crud.backend.services.exceptions.ManufactureDatabaseIntegrity;
import crud.backend.services.exceptions.ManufactureInvalidCNPJ;
import crud.backend.services.exceptions.ManufactureNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManufactureServices implements ManufactureBussiness {

    @Autowired
    private ManufactureRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ManufactureDTO manufactureSave(ManufactureDTO dto) {
        dto.setManufacturerCNPJ(Utils.unMaskCNPJ(dto.getManufacturerCNPJ()));
        ManufactureEntity entitySaved = new ManufactureEntity();
        if (Utils.validateDto(dto)) {
            if (!repository.findCnpj(dto.getManufacturerCNPJ())) {
                entitySaved = repository.save(modelMapper.map(dto, ManufactureEntity.class));
            } else {
                throw new ManufactureInvalidCNPJ("Duplicated CNPJ");
            }
        }

        dto = modelMapper.map(entitySaved, ManufactureDTO.class);
        dto.setManufacturerCNPJ(Utils.maskedCNPJ(dto.getManufacturerCNPJ()));
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManufactureDTO> manufactureFindAll() {
        List<ManufactureEntity> list = repository.findAll();
        return list.stream().map(ManufactureDTO::new).collect(Collectors.toList());
    }

    public Page<ManufactureDTO> manufacturePageFindAll(Pageable pageRequest) {
        try {
            Page<ManufactureEntity> page = repository.findAll(pageRequest);
            return page.map(ManufactureDTO::new);
        } catch (RuntimeException e) {
            throw new ManufactureDatabaseIntegrity("Error loading pages", e.fillInStackTrace() );
        }
    }

    @Override
    public ManufactureDTO manufactureFindById(Long id) {
        Optional<ManufactureEntity> entity = repository.findById(id);
            if (!entity.isEmpty())
                entity.get().setManufacturerCNPJ(Utils.maskedCNPJ(entity.get().getManufacturerCNPJ()));

        return new ManufactureDTO(entity.orElseThrow(()-> new ManufactureNotFoundException("Manufacture Not Found")));
    }

    @Override
    @Transactional
    public ManufactureDTO manufactureUpdate(Long id, ManufactureDTO dto) {
        try {
            ManufactureEntity entity = repository.getReferenceById(id);
                entity.setManufacturerName(dto.getManufacturerName());
                entity.setManufacturerCNPJ(Utils.unMaskCNPJ(dto.getManufacturerCNPJ()));
                entity.setManufacturerFantasyName(dto.getManufacturerFantasyName());
                entity.setManufacturerSocialName(dto.getManufacturerSocialName());
                entity.setManufacturerActive(dto.isManufacturerActive());
                entity.setManufacturerSite(dto.getManufacturerSite());
                entity.setManufacturerCountry(dto.getManufacturerCountry());
                entity.setManufacturerCity(dto.getManufacturerCity());
                entity.setManufactureNeighbourhood(dto.getManufactureNeighbourhood());
            repository.save(entity);
            return new ManufactureDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ManufactureNotFoundException("Manufacture Not Found");
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String manufactureDelete(Long id) {
        if (repository.existsById(id)){
            try {
                repository.deleteById(id);
                return "Deleted successfully";
            } catch (DataIntegrityViolationException e){
                throw new ManufactureDatabaseIntegrity("Database Violation", e.getCause());
            }
        }
            throw new ManufactureNotFoundException("Manufacture Not Found");
    }

}
