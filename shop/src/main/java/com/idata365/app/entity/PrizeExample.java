package com.idata365.app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PrizeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrizeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPrizeidIsNull() {
            addCriterion("prizeId is null");
            return (Criteria) this;
        }

        public Criteria andPrizeidIsNotNull() {
            addCriterion("prizeId is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeidEqualTo(Long value) {
            addCriterion("prizeId =", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidNotEqualTo(Long value) {
            addCriterion("prizeId <>", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidGreaterThan(Long value) {
            addCriterion("prizeId >", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidGreaterThanOrEqualTo(Long value) {
            addCriterion("prizeId >=", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidLessThan(Long value) {
            addCriterion("prizeId <", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidLessThanOrEqualTo(Long value) {
            addCriterion("prizeId <=", value, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidIn(List<Long> values) {
            addCriterion("prizeId in", values, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidNotIn(List<Long> values) {
            addCriterion("prizeId not in", values, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidBetween(Long value1, Long value2) {
            addCriterion("prizeId between", value1, value2, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizeidNotBetween(Long value1, Long value2) {
            addCriterion("prizeId not between", value1, value2, "prizeid");
            return (Criteria) this;
        }

        public Criteria andPrizenameIsNull() {
            addCriterion("prizeName is null");
            return (Criteria) this;
        }

        public Criteria andPrizenameIsNotNull() {
            addCriterion("prizeName is not null");
            return (Criteria) this;
        }

        public Criteria andPrizenameEqualTo(String value) {
            addCriterion("prizeName =", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameNotEqualTo(String value) {
            addCriterion("prizeName <>", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameGreaterThan(String value) {
            addCriterion("prizeName >", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameGreaterThanOrEqualTo(String value) {
            addCriterion("prizeName >=", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameLessThan(String value) {
            addCriterion("prizeName <", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameLessThanOrEqualTo(String value) {
            addCriterion("prizeName <=", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameLike(String value) {
            addCriterion("prizeName like", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameNotLike(String value) {
            addCriterion("prizeName not like", value, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameIn(List<String> values) {
            addCriterion("prizeName in", values, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameNotIn(List<String> values) {
            addCriterion("prizeName not in", values, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameBetween(String value1, String value2) {
            addCriterion("prizeName between", value1, value2, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizenameNotBetween(String value1, String value2) {
            addCriterion("prizeName not between", value1, value2, "prizename");
            return (Criteria) this;
        }

        public Criteria andPrizedescIsNull() {
            addCriterion("prizeDesc is null");
            return (Criteria) this;
        }

        public Criteria andPrizedescIsNotNull() {
            addCriterion("prizeDesc is not null");
            return (Criteria) this;
        }

        public Criteria andPrizedescEqualTo(String value) {
            addCriterion("prizeDesc =", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescNotEqualTo(String value) {
            addCriterion("prizeDesc <>", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescGreaterThan(String value) {
            addCriterion("prizeDesc >", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescGreaterThanOrEqualTo(String value) {
            addCriterion("prizeDesc >=", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescLessThan(String value) {
            addCriterion("prizeDesc <", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescLessThanOrEqualTo(String value) {
            addCriterion("prizeDesc <=", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescLike(String value) {
            addCriterion("prizeDesc like", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescNotLike(String value) {
            addCriterion("prizeDesc not like", value, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescIn(List<String> values) {
            addCriterion("prizeDesc in", values, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescNotIn(List<String> values) {
            addCriterion("prizeDesc not in", values, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescBetween(String value1, String value2) {
            addCriterion("prizeDesc between", value1, value2, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andPrizedescNotBetween(String value1, String value2) {
            addCriterion("prizeDesc not between", value1, value2, "prizedesc");
            return (Criteria) this;
        }

        public Criteria andIsMarketableIsNull() {
            addCriterion("is_marketable is null");
            return (Criteria) this;
        }

        public Criteria andIsMarketableIsNotNull() {
            addCriterion("is_marketable is not null");
            return (Criteria) this;
        }

        public Criteria andIsMarketableEqualTo(Integer value) {
            addCriterion("is_marketable =", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableNotEqualTo(Integer value) {
            addCriterion("is_marketable <>", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableGreaterThan(Integer value) {
            addCriterion("is_marketable >", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_marketable >=", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableLessThan(Integer value) {
            addCriterion("is_marketable <", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableLessThanOrEqualTo(Integer value) {
            addCriterion("is_marketable <=", value, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableIn(List<Integer> values) {
            addCriterion("is_marketable in", values, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableNotIn(List<Integer> values) {
            addCriterion("is_marketable not in", values, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableBetween(Integer value1, Integer value2) {
            addCriterion("is_marketable between", value1, value2, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andIsMarketableNotBetween(Integer value1, Integer value2) {
            addCriterion("is_marketable not between", value1, value2, "isMarketable");
            return (Criteria) this;
        }

        public Criteria andPrizepicIsNull() {
            addCriterion("prizePic is null");
            return (Criteria) this;
        }

        public Criteria andPrizepicIsNotNull() {
            addCriterion("prizePic is not null");
            return (Criteria) this;
        }

        public Criteria andPrizepicEqualTo(String value) {
            addCriterion("prizePic =", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicNotEqualTo(String value) {
            addCriterion("prizePic <>", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicGreaterThan(String value) {
            addCriterion("prizePic >", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicGreaterThanOrEqualTo(String value) {
            addCriterion("prizePic >=", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicLessThan(String value) {
            addCriterion("prizePic <", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicLessThanOrEqualTo(String value) {
            addCriterion("prizePic <=", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicLike(String value) {
            addCriterion("prizePic like", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicNotLike(String value) {
            addCriterion("prizePic not like", value, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicIn(List<String> values) {
            addCriterion("prizePic in", values, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicNotIn(List<String> values) {
            addCriterion("prizePic not in", values, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicBetween(String value1, String value2) {
            addCriterion("prizePic between", value1, value2, "prizepic");
            return (Criteria) this;
        }

        public Criteria andPrizepicNotBetween(String value1, String value2) {
            addCriterion("prizePic not between", value1, value2, "prizepic");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIsNull() {
            addCriterion("originalPrice is null");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIsNotNull() {
            addCriterion("originalPrice is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceEqualTo(BigDecimal value) {
            addCriterion("originalPrice =", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotEqualTo(BigDecimal value) {
            addCriterion("originalPrice <>", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceGreaterThan(BigDecimal value) {
            addCriterion("originalPrice >", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPrice >=", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceLessThan(BigDecimal value) {
            addCriterion("originalPrice <", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("originalPrice <=", value, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceIn(List<BigDecimal> values) {
            addCriterion("originalPrice in", values, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotIn(List<BigDecimal> values) {
            addCriterion("originalPrice not in", values, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPrice between", value1, value2, "originalprice");
            return (Criteria) this;
        }

        public Criteria andOriginalpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("originalPrice not between", value1, value2, "originalprice");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueIsNull() {
            addCriterion("diamondValue is null");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueIsNotNull() {
            addCriterion("diamondValue is not null");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueEqualTo(Integer value) {
            addCriterion("diamondValue =", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueNotEqualTo(Integer value) {
            addCriterion("diamondValue <>", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueGreaterThan(Integer value) {
            addCriterion("diamondValue >", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueGreaterThanOrEqualTo(Integer value) {
            addCriterion("diamondValue >=", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueLessThan(Integer value) {
            addCriterion("diamondValue <", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueLessThanOrEqualTo(Integer value) {
            addCriterion("diamondValue <=", value, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueIn(List<Integer> values) {
            addCriterion("diamondValue in", values, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueNotIn(List<Integer> values) {
            addCriterion("diamondValue not in", values, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueBetween(Integer value1, Integer value2) {
            addCriterion("diamondValue between", value1, value2, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andDiamondvalueNotBetween(Integer value1, Integer value2) {
            addCriterion("diamondValue not between", value1, value2, "diamondvalue");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsIsNull() {
            addCriterion("prizeDetailPics is null");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsIsNotNull() {
            addCriterion("prizeDetailPics is not null");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsEqualTo(String value) {
            addCriterion("prizeDetailPics =", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsNotEqualTo(String value) {
            addCriterion("prizeDetailPics <>", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsGreaterThan(String value) {
            addCriterion("prizeDetailPics >", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsGreaterThanOrEqualTo(String value) {
            addCriterion("prizeDetailPics >=", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsLessThan(String value) {
            addCriterion("prizeDetailPics <", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsLessThanOrEqualTo(String value) {
            addCriterion("prizeDetailPics <=", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsLike(String value) {
            addCriterion("prizeDetailPics like", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsNotLike(String value) {
            addCriterion("prizeDetailPics not like", value, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsIn(List<String> values) {
            addCriterion("prizeDetailPics in", values, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsNotIn(List<String> values) {
            addCriterion("prizeDetailPics not in", values, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsBetween(String value1, String value2) {
            addCriterion("prizeDetailPics between", value1, value2, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailpicsNotBetween(String value1, String value2) {
            addCriterion("prizeDetailPics not between", value1, value2, "prizedetailpics");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsIsNull() {
            addCriterion("prizeDetailTexts is null");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsIsNotNull() {
            addCriterion("prizeDetailTexts is not null");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsEqualTo(String value) {
            addCriterion("prizeDetailTexts =", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsNotEqualTo(String value) {
            addCriterion("prizeDetailTexts <>", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsGreaterThan(String value) {
            addCriterion("prizeDetailTexts >", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsGreaterThanOrEqualTo(String value) {
            addCriterion("prizeDetailTexts >=", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsLessThan(String value) {
            addCriterion("prizeDetailTexts <", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsLessThanOrEqualTo(String value) {
            addCriterion("prizeDetailTexts <=", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsLike(String value) {
            addCriterion("prizeDetailTexts like", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsNotLike(String value) {
            addCriterion("prizeDetailTexts not like", value, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsIn(List<String> values) {
            addCriterion("prizeDetailTexts in", values, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsNotIn(List<String> values) {
            addCriterion("prizeDetailTexts not in", values, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsBetween(String value1, String value2) {
            addCriterion("prizeDetailTexts between", value1, value2, "prizedetailtexts");
            return (Criteria) this;
        }

        public Criteria andPrizedetailtextsNotBetween(String value1, String value2) {
            addCriterion("prizeDetailTexts not between", value1, value2, "prizedetailtexts");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}