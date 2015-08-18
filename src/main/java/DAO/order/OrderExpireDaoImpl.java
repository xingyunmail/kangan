package DAO.order;

import model.order.OrderExpireModel;

/**
 * Created by scar on 15/5/26.
 */
public class OrderExpireDaoImpl {

    /**
     * 到期列表
     * @param orderExpireModel
     * @return
     */
    public String getExpireOrderList(OrderExpireModel orderExpireModel) {

        StringBuffer sb = new StringBuffer();
        if (orderExpireModel.getSearchType() == 0) {
            sb.append("SELECT c.areaName, c.areaId, e.userName, e.userId, d.orderid, d.custName, d.custId, d.custPhone, d.custAddr, b.endDate, b.leftCount FROM ( SELECT a.orderId, sum(a.leftDays * a.quantity) leftCount, MAX(a.endDate) endDate FROM order_detail a WHERE a. STATUS = 1 GROUP BY a.orderId ) b, area_info c, order_info d, user_info e WHERE b.orderId = d.orderid AND d.deliverId = e.userId AND e.areaId = c.areaId ");
            if (orderExpireModel.getStartDate() != null && orderExpireModel.getStartDate().length() > 0) {
                sb.append(" AND b.endDate >= #{startDate} ");
            }
            if (orderExpireModel.getEndDate() != null && orderExpireModel.getEndDate().length() > 0) {
                sb.append(" AND b.endDate <= #{endDate} ");
            }
        } else {
            sb.append("SELECT c.areaName, c.areaId, e.userName, e.userId, d.orderid, d.custName, d.custId, d.custPhone, d.custAddr, b.minEndDate, b.maxEndDate,b.maxEndDate endDate, b.leftCount FROM ( SELECT a.orderId, sum(a.leftDays * a.quantity) leftCount, min(a.endDate) minEndDate, MAX(a.endDate) maxEndDate FROM order_detail a WHERE a. STATUS = 1 GROUP BY a.orderId ) b, area_info c, order_info d, user_info e WHERE b.orderId = d.orderid AND d.deliverId = e.userId AND e.areaId = c.areaId");
            if (orderExpireModel.getStartDate() != null && orderExpireModel.getStartDate().length() > 0) {
                sb.append(" AND b.minEndDate >= #{startDate} ");
            }
            if (orderExpireModel.getEndDate() != null && orderExpireModel.getEndDate().length() > 0) {
                sb.append(" AND b.maxEndDate >= #{endDate} ");
            }
        }

        if (orderExpireModel.getAreaId() != null && orderExpireModel.getAreaId().length() > 0) {
            sb.append(" AND c.areaId in (" + orderExpireModel.getAreaId() + ")");
        }
        if (orderExpireModel.getUserId() != null && orderExpireModel.getUserId().length() > 0) {
            sb.append(" AND e.userId =#{userId} ");
        }
        if (orderExpireModel.getLeftMin() != null && orderExpireModel.getLeftMin().length() > 0) {
            sb.append(" AND b.leftCount >= #{leftMin} ");
        }
        if (orderExpireModel.getLeftMax() != null && orderExpireModel.getLeftMax().length() > 0) {
            sb.append(" AND b.leftCount <= #{leftMax} ");
        }
        if (orderExpireModel.getLimit() > 0) {
            sb.append(" limit " + (orderExpireModel.getStartNum() - 1) * orderExpireModel.getLimit() + "," + orderExpireModel.getLimit());
        }
        return sb.toString();
    }

    /**
     * 到期计数
     * @param orderExpireModel
     * @return
     */
    public String getExpireOrderCount(OrderExpireModel orderExpireModel) {

        StringBuffer sb = new StringBuffer();
        if (orderExpireModel.getSearchType() == 0) {
            sb.append("SELECT count(*) FROM ( SELECT a.orderId, sum(a.leftDays * a.quantity) leftCount, MAX(a.endDate) endDate FROM order_detail a WHERE a. STATUS = 1 GROUP BY a.orderId ) b, area_info c, order_info d, user_info e WHERE b.orderId = d.orderid AND d.deliverId = e.userId AND e.areaId = c.areaId ");
            if (orderExpireModel.getStartDate() != null && orderExpireModel.getStartDate().length() > 0) {
                sb.append(" AND b.endDate >= #{startDate} ");
            }
            if (orderExpireModel.getEndDate() != null && orderExpireModel.getEndDate().length() > 0) {
                sb.append(" AND b.endDate <= #{endDate} ");
            }
        } else {
            sb.append("SELECT count(*)  FROM ( SELECT a.orderId, sum(a.leftDays * a.quantity) leftCount, min(a.endDate) minEndDate, MAX(a.endDate) maxEndDate FROM order_detail a WHERE a. STATUS = 1 GROUP BY a.orderId ) b, area_info c, order_info d, user_info e WHERE b.orderId = d.orderid AND d.deliverId = e.userId AND e.areaId = c.areaId");
            if (orderExpireModel.getStartDate() != null && orderExpireModel.getStartDate().length() > 0) {
                sb.append(" AND b.minEndDate >= #{startDate} ");
            }
            if (orderExpireModel.getEndDate() != null && orderExpireModel.getEndDate().length() > 0) {
                sb.append(" AND b.maxEndDate >= #{endDate} ");
            }
        }

        if (orderExpireModel.getAreaId() != null && orderExpireModel.getAreaId().length() > 0) {
            sb.append(" AND c.areaId in (" + orderExpireModel.getAreaId() + ")");
        }
        if (orderExpireModel.getUserId() != null && orderExpireModel.getUserId().length() > 0) {
            sb.append(" AND e.userId =#{userId} ");
        }
        if (orderExpireModel.getLeftMin() != null && orderExpireModel.getLeftMin().length() > 0) {
            sb.append(" AND b.leftCount >= #{leftMin} ");
        }
        if (orderExpireModel.getLeftMax() != null && orderExpireModel.getLeftMax().length() > 0) {
            sb.append(" AND b.leftCount <= #{leftMax} ");
        }
        return sb.toString();
    }

}
