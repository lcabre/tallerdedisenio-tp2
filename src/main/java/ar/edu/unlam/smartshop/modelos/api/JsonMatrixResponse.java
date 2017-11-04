package ar.edu.unlam.smartshop.modelos.api;

import java.util.List;

public class JsonMatrixResponse {
    List<String> destination_addresses;
    List<String> origin_addresses;
    List<Row> rows;
    String status;

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Integer getMenorDistanciaIndex(){
        Integer index=null;
        Integer distanciaMinima=null;

        for (Element element:this.rows.get(0).getElements()) {
            if(distanciaMinima == null) {
                distanciaMinima = element.getDistance().getValue();
                index = 0;
            }

            if(element.getDistance().getValue()<distanciaMinima){
                distanciaMinima = element.getDistance().getValue();
                index++;
            }
        }

        return index;
    }

    public Distance get(Integer i){
        return this.rows.get(0).getElements().get(i).getDistance();
    }
}
