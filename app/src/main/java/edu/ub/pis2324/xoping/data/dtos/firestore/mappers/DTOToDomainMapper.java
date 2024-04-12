package edu.ub.pis2324.xoping.data.dtos.firestore.mappers;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.UUID;

import edu.ub.pis2324.xoping.domain.model.valueobjects.ClientId;
import edu.ub.pis2324.xoping.domain.model.valueobjects.ProductId;

/*
 * This class is a singleton that provides a generic ModelMapper instance.
 */
public class DTOToDomainMapper extends ModelMapper {

  public DTOToDomainMapper() {
    super();

    super.getConfiguration()
      .setFieldMatchingEnabled(true) // No need to define setters
      .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
      .setMatchingStrategy(MatchingStrategies.LOOSE);

    super.addConverter(new AbstractConverter<String, ClientId>() {
      @Override
      protected ClientId convert(String source) {
        return new ClientId(source);
      }
    });

    super.addConverter(new AbstractConverter<String, ProductId>() {
      @Override
      protected ProductId convert(String source) {
        return new ProductId(source);
      }
    });
  }

  /*
   * Mapeja un objecte a un altre objecte de la classe especificada.
   * No caldria definir-lo perquè la classe ModelMapper ja té un mètode
   * amb la mateixa signatura, però així podem fer que la classe implementi
   * l'interfície DataMapper i veiem que si no re-aprofitéssim ModelMapper
   * hauríem de definir-lo.
   */
  @Override
  public <T> T map(Object source, Class<T> destinationType) {
    return super.map(source, destinationType);
  }
}
