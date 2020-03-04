package se.kry.codetest.data.service;

import io.vertx.core.Future;
import io.vertx.ext.sql.ResultSet;
import se.kry.codetest.DBConnector;
import se.kry.codetest.data.model.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesService {

    private DBConnector connector;

    public ServicesService(DBConnector connector) {
        this.connector = connector;
    }

    public void addService(String serviceUrl) {
        ResultSet resultSet = connector.query(String.format("INSERT INTO service(url) values('%s')", serviceUrl)).result();
        System.out.println(resultSet);
    }

    public List<Service> getAllServices() {
        Future<ResultSet> futureResults = connector.query("SELECT * FROM service");
        List<Service> resultList = futureResults.map(results -> {
            List<Service> services = results.getRows().stream().map(row -> {
                Service service = new Service();
                service.setUrl(row.getString("url"));
                return service;
            }).collect(Collectors.toList());
            return services;
        }).result();
        return resultList;
    }
}
