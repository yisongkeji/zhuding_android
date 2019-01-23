package chat.foreseers.com.foreseers.bean;


public class LocationBean {

    //国
    String country;
    //省
    String city;
    //市
    String area;
    //区
    String addr;
    //街
    String addrs;
    //经度
    double lat;

    //维度

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {

        return lat;
    }

    public double getLng() {
        return lng;
    }

    double lng;


    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setAddrs(String addrs) {
        this.addrs = addrs;
    }

    public String getCountry() {

        return country;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getAddr() {
        return addr;
    }

    public String getAddrs() {
        return addrs;
    }
}
