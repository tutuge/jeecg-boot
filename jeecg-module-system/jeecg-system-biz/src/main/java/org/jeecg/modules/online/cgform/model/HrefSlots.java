 package org.jeecg.modules.online.cgform.model;
 
 public class HrefSlots {
   private String slotName;
   
   private String href;
   
   public void setSlotName(String slotName) {
     this.slotName = slotName;
   }
   
   public void setHref(String href) {
     this.href = href;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof HrefSlots))
       return false; 
     HrefSlots hrefSlots = (HrefSlots)o;
     if (!hrefSlots.canEqual(this))
       return false; 
     String str1 = getSlotName(), str2 = hrefSlots.getSlotName();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getHref(), str4 = hrefSlots.getHref();
     return !((str3 == null) ? (str4 != null) : !str3.equals(str4));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof HrefSlots;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str1 = getSlotName();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getHref();
     return null * 59 + ((str2 == null) ? 43 : str2.hashCode());
   }
   
   public String toString() {
     return "HrefSlots(slotName=" + getSlotName() + ", href=" + getHref() + ")";
   }
   
   public String getSlotName() {
     return this.slotName;
   }
   
   public String getHref() {
     return this.href;
   }
   
   public HrefSlots() {}
   
   public HrefSlots(String slotName, String href) {
     this.slotName = slotName;
     this.href = href;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\HrefSlots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
