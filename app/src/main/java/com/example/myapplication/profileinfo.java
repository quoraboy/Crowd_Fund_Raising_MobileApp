package com.example.myapplication;



public class profileinfo {

    private String Name;
    private  String Age;
    private  String Qualification;
    private String Organization;

  public profileinfo()
  {

  }
    public profileinfo(String name, String age, String qualification, String organization) {
        Name = name;
        Age = age;
        Qualification = qualification;
        Organization = organization;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String organization) {
        Organization = organization;
    }


}
