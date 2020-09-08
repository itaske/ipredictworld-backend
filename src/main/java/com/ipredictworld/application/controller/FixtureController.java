package com.ipredictworld.application.controller;

import com.ipredictworld.application.entities.Fixture;
import com.ipredictworld.application.repository.FixtureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.GeneratedValue;
import java.util.function.Function;

@Controller
public class FixtureController extends GenericController<Fixture, Fixture, FixtureRepository> {

    @Autowired
    FixtureRepository fixtureRepository;

    Function<Fixture,Fixture> conversionFunction = (fixture)-> fixture;

    public FixtureController(){
        setConversionFunction(conversionFunction);
        setTypeParameterClass(Fixture.class);
        setJpaRepository(fixtureRepository);
    }


}
