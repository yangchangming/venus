
package venus.portal.requests.vo;

import venus.frames.base.vo.BaseValueObject;
import venus.portal.helper.EwpVoHelper;

import java.sql.Timestamp;

public class RequestsVo extends BaseValueObject implements Cloneable {
    private String id;
    
    private String document_id;
        
    private String first_name;
        
    private String last_name;
     
    private String title;
        
    private String company;
        
    private String email;
        
    private String phone;
        
    private String country;
        
    private String website;
        
    private String referer;
        
    private String comment;
        
    private Timestamp create_time;

    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getDocument_id(){
        return document_id;
    }
    
    public void setDocument_id(String document_id){
        this.document_id = document_id;
    }
    
    public String getFirst_name(){
        return first_name;
    }
    
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    
    public String getLast_name(){
        return last_name;
    }

    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getCompany(){
        return company;
    }
    
    public void setCompany(String company){
        this.company = company;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public String getCountry(){
        return country;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public String getWebsite(){
        return website;
    }
    
    public void setWebsite(String website){
        this.website = website;
    }
    
    public String getReferer(){
        return referer;
    }
    
    public void setReferer(String referer){
        this.referer = referer;
    }
    
    public String getComment(){
        return comment;
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public boolean equals(Object other) {
        return EwpVoHelper.voEquals(this, other);
    }

    public int hashCode() {
        return EwpVoHelper.voHashCode(this);
    }
    
    public Object clone() {
        return EwpVoHelper.voClone(this);
    }

    public String toString() {
        return super.toString() + ":" + EwpVoHelper.voToString(this);
    }
    
}	
