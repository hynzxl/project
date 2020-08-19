package com.pinyougou.group;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

//此类对应规格和规格参数
public class SpecificationOption implements Serializable {

    private TbSpecification specification;
    private List<TbSpecificationOption> SpeOptions;

    public SpecificationOption() {
    }

    public SpecificationOption(TbSpecification specification, List<TbSpecificationOption> speOptions) {
        this.specification = specification;
        SpeOptions = speOptions;
    }

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpeOptions() {
        return SpeOptions;
    }

    public void setSpeOptions(List<TbSpecificationOption> speOptions) {
        SpeOptions = speOptions;
    }

    @Override
    public String toString() {
        return "SpecificationOption{" +
                "specification=" + specification +
                ", SpeOptions=" + SpeOptions +
                '}';
    }
}
