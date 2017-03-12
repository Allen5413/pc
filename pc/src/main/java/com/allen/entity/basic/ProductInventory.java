package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 包路径：com.allen.entity.basic
 * 功能说明：产品库存信息
 * 创建人： ly
 * 创建时间: 2017-03-07 20:40
 */
@Entity
@Table(name="product_inventory")
public class ProductInventory implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private long productId;//产品id
    private BigDecimal min;//最小库存
    private BigDecimal max;//最大库存
    private BigDecimal safe;//安全库存
    private BigDecimal productNum;//产品库存量

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getSafe() {
        return safe;
    }

    public void setSafe(BigDecimal safe) {
        this.safe = safe;
    }

    public BigDecimal getProductNum() {
        return productNum;
    }

    public void setProductNum(BigDecimal productNum) {
        this.productNum = productNum;
    }
}
