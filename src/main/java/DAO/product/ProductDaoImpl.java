package DAO.product;

import model.product.ProdModel;
import org.springframework.util.StringUtils;

/**
 * Created by sunhao on 15/5/4.
 */
public class ProductDaoImpl {

    public String selectMessage(ProdModel prodModel) {
        String sql = "select pinfo.prodId prodId,pinfo.prodName prodName,pinfo.prodPakage prodPakage,pinfo.prodStatus prodStatus,pinfo.insertDate insertDate, " +
                "  pi.prodType prodType,pi.priceId priceId,pi.price price ,pi.priceType priceType,pi.valid valid,pi.customer customer " +
                " from product_info pinfo " +
                " LEFT JOIN price_info pi on pi.prodId = pinfo.prodId  ";

        //查询所有 1 商品 2 赠品 3 赠奶
        if (prodModel.getProdId() != null) {
            //根据ID查询
            if (prodModel.getProdType().equals("1")) {
                prodModel.setProdType("1");
            } else {
                prodModel.setProdType("2,3");
            }
            sql += "where pinfo.prodId='" + prodModel.getProdId() + "' and pi.prodType in (" + prodModel.getProdType() + ") and pi.priceType in(1,2,3,4) and pi.valid=1 and pi.customer=0 ";
        } else if (prodModel.getProdType().equals("2")) {//查询列表
            prodModel.setProdType("2,3");
            sql += "where  pi.prodType in (" + prodModel.getProdType() + ") and pi.priceType in(1,2,3,4) and pi.valid=1 and pi.customer=0 ";
        } else {
            sql += " where pi.prodType=" + prodModel.getProdType() + " and pi.priceType in(1,2,3,4) and pi.valid=1 and pi.customer=0";
        }

        sql += " GROUP BY pinfo.prodName order by pinfo.prodId ";

        System.out.println("牛奶信息：" + sql);

        return sql;
    }

    public String getProductByCity(ProdModel prodModel) {
        String sql = "  select pinfo.prodId prodId,pinfo.prodName prodName,pinfo.prodPakage prodPakage,pinfo.prodStatus prodStatus,pinfo.insertDate insertDate, " +
                " pi.prodType prodType,pi.priceId priceId,pi.price price ,pi.priceType priceType,pi.valid valid,pi.customer customer,pinfo.prodweight prodweight " +
                " from product_info pinfo " +
                 " LEFT JOIN price_info pi on pi.prodId = pinfo.prodId  ";
        //查询所有 1 商品 2 赠品 3 赠奶
        sql += " where pi.prodType in(1,2,3) and pi.priceType in("+prodModel.getPriceType()+") and pi.valid=1 and pi.customer=0";

        if(!StringUtils.isEmpty(prodModel.getProdName())){
            sql += " and pinfo.prodName like '%"+prodModel.getProdName()+"%'";
        }

        sql += " GROUP BY pinfo.prodName   ORDER BY prodId DESC ";
        if (prodModel.getLimit() > 0) {
            sql += " limit " + (prodModel.getStartNum() - 1) * prodModel.getLimit() + "," + prodModel.getLimit();
        }
        return sql;
    }
    public String getProductByCityCount(ProdModel prodModel) {
        String sql = "  select count(*) " +
                " from product_info pinfo " +
                " LEFT JOIN price_info pi on pi.prodId = pinfo.prodId  ";
        //查询所有 1 商品 2 赠品 3 赠奶
        sql += " where pi.prodType in(1,2,3) and pi.priceType in("+prodModel.getPriceType()+") and pi.valid=1 and pi.customer=0";
        if(!StringUtils.isEmpty(prodModel.getProdName())){
            sql += " and pinfo.prodName like '%"+prodModel.getProdName()+"%'";
        }
        return sql;
    }


    public String getProductById(ProdModel prodModel) {
        String sql = "  select pinfo.prodId prodId,pinfo.prodName prodName,pinfo.prodPakage prodPakage,pinfo.prodStatus prodStatus,pinfo.insertDate insertDate, " +
                " pi.prodType prodType,pi.priceId priceId,pi.price price ,pi.priceType priceType,pi.valid valid,pi.customer customer,pi.beginDate beginDate ,pi.endDate endDate ,pinfo.prodweight prodweight" +
                " from product_info pinfo " +
                " LEFT JOIN price_info pi on pi.prodId = pinfo.prodId  ";
        sql += " where pi.prodId ="+prodModel.getProdId()+" and pi.priceType in("+prodModel.getPriceType()+") and pi.valid=1 and pi.customer=0";

        sql += " GROUP BY pinfo.prodName   ORDER BY prodId DESC ";
        return sql;
    }



    public String getProdPriceInfo(ProdModel prodModel) {
//默认查询所有商品信息，客户ID不为空时，查询对应客户ID的所有修改记录记录

        /**
         * 1、查看所有商品ID为1的信息
         * 2、根据客户ID查看所有的价格修改记录
         * 3、根据客户ID查看所有生效时间，无结束时间的商品-价格，供修改，（附）前期对应的客户的商品自动录入到数据库中
         */
        String sql = "select pi.price,pdi.prodId,pdi.prodName,pi.customer custId,pi.beginDate beginDate,pi.endDate endDate,pi.updateTime stopDate from  product_info pdi ";
        if (prodModel.getCustId() != null && "" != prodModel.getCustId()) {//2、根据客户ID查看所有的价格修改记录
            sql += " LEFT JOIN price_info pi on pdi.prodId = pi.prodId where pi.prodType=1 and pi.customer=" + prodModel.getCustId()
                    + "GROUP BY pdi.prodId";
        } else {//1、查看所有商品ID为1的信息
            sql += "LEFT JOIN price_info pi on pdi.prodId = pi.prodId where pi.prodType=1 GROUP BY pdi.prodId";
        }
        return sql;
    }
}
