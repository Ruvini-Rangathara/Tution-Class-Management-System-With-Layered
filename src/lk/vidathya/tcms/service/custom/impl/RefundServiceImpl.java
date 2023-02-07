package lk.vidathya.tcms.service.custom.impl;

import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.IncomeAndExpenditureDAO;
import lk.vidathya.tcms.dao.custom.RefundDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.RefundDTO;
import lk.vidathya.tcms.entity.IncomeAndExpenditure;
import lk.vidathya.tcms.service.custom.RefundService;
import lk.vidathya.tcms.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RefundServiceImpl implements RefundService {
    private final RefundDAO refundDAO;
    private final IncomeAndExpenditureDAO incomeAndExpenditureDAO;
    private final Convertor convertor;
    private final Connection connection;

    public RefundServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        refundDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.REFUND_DAO_IMPL);
        incomeAndExpenditureDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.INCOME_AND_EXPENDITURE_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(RefundDTO refundDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isAddRefund = refundDAO.add(convertor.toRefund(refundDTO));
            if (isAddRefund) {
                int year = Integer.parseInt(refundDTO.getDate().split("-")[0]);
                IncomeAndExpenditure budget = new IncomeAndExpenditure(
                        refundDTO.getStaffId(),
                        "Expenditure",
                        "Refund",
                        refundDTO.getAmount(),
                        year,
                        refundDTO.getMonth(),
                        refundDTO.getDate()
                );
                boolean isAddBudget = incomeAndExpenditureDAO.add(budget);
                if (isAddBudget) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(RefundDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RefundDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public List getAllByMonth(String month) throws SQLException, ClassNotFoundException {
        return refundDAO.getAllByMonth(month);
    }
}
