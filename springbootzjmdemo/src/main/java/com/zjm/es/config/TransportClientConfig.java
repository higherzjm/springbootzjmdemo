package com.zjm.es.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;

import java.net.InetAddress;
import java.net.UnknownHostException;

//@Configuration   跟spring动态集成不需要此配置
public class TransportClientConfig extends ElasticsearchConfigurationSupport {

/*  @Bean
  public Client elasticsearchClient() throws UnknownHostException {
    Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
    TransportClient client = new PreBuiltTransportClient(settings);
    client.addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.6.204"), 9200));
    return client;
  }

  @Bean(name = {"elasticsearchOperations", "elasticsearchTemplate"})
  public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
  	return new ElasticsearchTemplate(elasticsearchClient(), entityMapper());
  }

  // use the ElasticsearchEntityMapper
  @Bean
  @Override
  public EntityMapper entityMapper() {
    ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
  	  new DefaultConversionService());
    entityMapper.setConversions(elasticsearchCustomConversions());
    return entityMapper;
  }*/
}