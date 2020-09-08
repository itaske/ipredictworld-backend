package com.ipredictworld.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipredictworld.application.response.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GenericController<T,R extends Response, P extends JpaRepository> {

    @Autowired
    private P jpaRepository;

    private Function<T,R> conversionFunction;
    private Class<T> typeParameterClass;

    public GenericController(){}



    public GenericController(P jpaRepository, Function<T,R> conversionFunction, Class<T> typeParameterClass){
      setJpaRepository(jpaRepository);
      setConversionFunction(conversionFunction);
      this.typeParameterClass = typeParameterClass;
    }

    public Class<T> getTypeParameterClass() {
        return typeParameterClass;
    }

    public void setTypeParameterClass(Class<T> typeParameterClass){
        this.typeParameterClass = typeParameterClass;
    }


    public List<T> getAllList(int currrentPage, int size, String direction, String attribute ) {
        int copyOfCurrentPage = currrentPage;
        int copyOfSize = size;
        String copyOfDirection = direction;
        String copyOfAttribute = attribute;


        if (copyOfSize == 0) {
            copyOfSize = ControllerConstraints.PAGINATION_DEFAULT_SIZE;
        }

        if (direction == null || !StringUtils.hasText(direction)) {
            direction = "ASC";
        }

        List<String> attributeList = new LinkedList<>();
        attributeList.add(attribute);

        Optional<Pageable> pageable = Optional.empty();

        if (direction.equalsIgnoreCase("ASC")) {
            pageable = Optional.ofNullable(PageRequest.of(currrentPage, size, Sort.by(attribute).ascending()));

        } else if (direction.equalsIgnoreCase("DESC"))
            pageable = Optional.ofNullable(PageRequest.of(currrentPage, size, Sort.by(attribute).descending()));


        List<T> all;
        if (pageable.isPresent())
            all = (List<T>) jpaRepository.findAll(pageable.get()).get().collect(Collectors.toList());
        else
            all = jpaRepository.findAll();
        return all;
    }

    public ResponseEntity<?> getAll(int currrentPage, int size, String direction, String attribute ){
       List<T> all = getAllList(currrentPage, size, direction, attribute);
        if (all.isEmpty()){
            return ResponseEntity.noContent().build();
        } else{
            List<R> responses = all.stream()
                    .map(( T t) ->conversionFunction.apply(t)).collect(Collectors.toList());
            return ResponseEntity.ok().body(responses);
        }
    }



    public<P> ResponseEntity<R> getById( P id){
        Optional<T> optional = jpaRepository.findById(id);

        if (optional.isPresent()){
            return ResponseEntity.ok(conversionFunction.apply(optional.get()));
        } else{
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<R> createObject(T object){

      T savedObject = (T) jpaRepository.save(object);

      return new ResponseEntity<>(conversionFunction.apply(savedObject), HttpStatus.CREATED);

  }

   public<P> ResponseEntity<?> editObject(Map<String,Object> map, P id){

      if (jpaRepository.existsById(id)){

          T objectToBeEdited = (T) jpaRepository.findById(id).get();

          ObjectMapper objectMapper = new ObjectMapper();

          T copiedUser = (T) objectMapper.convertValue(map, typeParameterClass);

           copyProperties(copiedUser, objectToBeEdited, map.keySet());

           T editedObject = (T) jpaRepository.saveAndFlush(objectToBeEdited);
           Map<String, Object> response = new HashMap<>();
           response.put("status","success");
           response.put("data", conversionFunction.apply(editedObject));
          return new ResponseEntity<>(response, HttpStatus.OK);
       } else{
            return ResponseEntity.notFound().build();
        }
    }

    public<P> ResponseEntity<?> deleteObjectById(P id){

        if (jpaRepository.existsById(id)){
            jpaRepository.deleteById(id);
              return ResponseEntity.ok().build();
        }else{
        return ResponseEntity.notFound().build();
}
    }



    public P getJpaRepository() {
        return jpaRepository;
    }

    public void setJpaRepository(P jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Function<T, R> getConversionFunction() {
        return conversionFunction;
    }

    public void setConversionFunction(Function<T, R> conversionFunction) {
        this.conversionFunction = conversionFunction;
    }

    public static void copyProperties(Object src, Object trg, Set<String> props) {

        String[] excludedProperties =
                Arrays.stream(BeanUtils.getPropertyDescriptors(src.getClass()))
                        .map(PropertyDescriptor::getName)
                        .filter(name -> !props.contains(name))
                        .toArray(String[]::new);
       System.out.println(Arrays.toString(excludedProperties));
        BeanUtils.copyProperties(src, trg, excludedProperties);

    }
}
