package com.foreseers.chat.foreseers.bean;

public class PeopleBean {

        private long createTime;
        private long modifyTime;
        private String positionId;
        private String cuId;
        private String positionType;//职位类型 0：有薪，  1：无薪
        private String typeFirst;
        private String typeSecond;
        private Object typeThred;
        private String positionName;//职位名称
        private String positionDescript;//职位描述
        private Object getSkills;
        private Object skillsType;
        private Object interviewFlag;//是否面试
        private int peopleNumber;//企业所需总人数
        private Object interviewMatching;
        private String serviceCharg;
        private long workstartDay;//工作开始时间
        private long workendDay;//工作结束时间
        private String specificDay;//具体天数，例如：周一，周二
        private int totalDay;//该职位总天数
        private Object workstartTime;//每天工作开始时间
        private Object workendTime;//每天工作结束时间
        private long cutoffTime;
        private int salaryDay;//职位每天工资数
        private String settlementCycle;//结算方式，是周结还是日结还是其他
        private int totalSalary;//该职位所支付总工资，包括服务费和学生工资
        private String province;//省
        private String city;//市
        private String adress;//具体地址
        private String sex;//性别
        private String welfare;//福利
        private String urgent;//是否加急
        private String status;
        private String companyType;//企业类型  1:企业   2：政府   3：组织
        private String companyName;//公司名称
        private String companyShort;//公司简称
        private String studentNum;//已经预约的学生人数

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
        }

        public String getCuId() {
            return cuId;
        }

        public void setCuId(String cuId) {
            this.cuId = cuId;
        }

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public String getTypeFirst() {
            return typeFirst;
        }

        public void setTypeFirst(String typeFirst) {
            this.typeFirst = typeFirst;
        }

        public String getTypeSecond() {
            return typeSecond;
        }

        public void setTypeSecond(String typeSecond) {
            this.typeSecond = typeSecond;
        }

        public Object getTypeThred() {
            return typeThred;
        }

        public void setTypeThred(Object typeThred) {
            this.typeThred = typeThred;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getPositionDescript() {
            return positionDescript;
        }

        public void setPositionDescript(String positionDescript) {
            this.positionDescript = positionDescript;
        }

        public Object getGetSkills() {
            return getSkills;
        }

        public void setGetSkills(Object getSkills) {
            this.getSkills = getSkills;
        }

        public Object getSkillsType() {
            return skillsType;
        }

        public void setSkillsType(Object skillsType) {
            this.skillsType = skillsType;
        }

        public Object getInterviewFlag() {
            return interviewFlag;
        }

        public void setInterviewFlag(Object interviewFlag) {
            this.interviewFlag = interviewFlag;
        }

        public int getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(int peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public Object getInterviewMatching() {
            return interviewMatching;
        }

        public void setInterviewMatching(Object interviewMatching) {
            this.interviewMatching = interviewMatching;
        }

        public String getServiceCharg() {
            return serviceCharg;
        }

        public void setServiceCharg(String serviceCharg) {
            this.serviceCharg = serviceCharg;
        }

        public long getWorkstartDay() {
            return workstartDay;
        }

        public void setWorkstartDay(long workstartDay) {
            this.workstartDay = workstartDay;
        }

        public long getWorkendDay() {
            return workendDay;
        }

        public void setWorkendDay(long workendDay) {
            this.workendDay = workendDay;
        }

        public String getSpecificDay() {
            return specificDay;
        }

        public void setSpecificDay(String specificDay) {
            this.specificDay = specificDay;
        }

        public int getTotalDay() {
            return totalDay;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }

        public Object getWorkstartTime() {
            return workstartTime;
        }

        public void setWorkstartTime(Object workstartTime) {
            this.workstartTime = workstartTime;
        }

        public Object getWorkendTime() {
            return workendTime;
        }

        public void setWorkendTime(Object workendTime) {
            this.workendTime = workendTime;
        }

        public long getCutoffTime() {
            return cutoffTime;
        }

        public void setCutoffTime(long cutoffTime) {
            this.cutoffTime = cutoffTime;
        }

        public int getSalaryDay() {
            return salaryDay;
        }

        public void setSalaryDay(int salaryDay) {
            this.salaryDay = salaryDay;
        }

        public String getSettlementCycle() {
            return settlementCycle;
        }

        public void setSettlementCycle(String settlementCycle) {
            this.settlementCycle = settlementCycle;
        }

        public int getTotalSalary() {
            return totalSalary;
        }

        public void setTotalSalary(int totalSalary) {
            this.totalSalary = totalSalary;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getWelfare() {
            return welfare;
        }

        public void setWelfare(String welfare) {
            this.welfare = welfare;
        }

        public String getUrgent() {
            return urgent;
        }

        public void setUrgent(String urgent) {
            this.urgent = urgent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyShort() {
            return companyShort;
        }

        public void setCompanyShort(String companyShort) {
            this.companyShort = companyShort;
        }

        public String getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(String studentNum) {
            this.studentNum = studentNum;
        }

}
