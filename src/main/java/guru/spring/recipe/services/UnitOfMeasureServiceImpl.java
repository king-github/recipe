package guru.spring.recipe.services;

import guru.spring.recipe.commands.UnitOfMeasureCommand;
import guru.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.spring.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> getAllUoms() {

        return unitOfMeasureRepository.findAll().stream()
                                                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                                                .collect(Collectors.toSet());
    }

}
