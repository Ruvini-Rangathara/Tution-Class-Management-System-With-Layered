package lk.vidathya.tcms.service.custom.impl;


import lk.vidathya.tcms.dao.DaoFactory;
import lk.vidathya.tcms.dao.DaoTypes;
import lk.vidathya.tcms.dao.custom.ClassesDAO;
import lk.vidathya.tcms.dao.custom.HallReservationDAO;
import lk.vidathya.tcms.dao.custom.NotPaidTutorSalaryDAO;
import lk.vidathya.tcms.db.DBConnection;
import lk.vidathya.tcms.dto.ClassesDTO;
import lk.vidathya.tcms.dto.HallReservationDTO;
import lk.vidathya.tcms.dto.NotPaidTutorSalaryDTO;
import lk.vidathya.tcms.entity.Classes;
import lk.vidathya.tcms.service.custom.ClassesServices;
import lk.vidathya.tcms.service.util.Convertor;
import lk.vidathya.tcms.util.MonthName;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClassesServiceImpl implements ClassesServices {
    private final ClassesDAO classesDAO;
    private final NotPaidTutorSalaryDAO notPaidTutorSalaryDAO;
    private final HallReservationDAO hallReservationDAO;
    private final Convertor convertor;
    private final Connection connection;

    public ClassesServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
        classesDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.CLASS_DAO_IMPL);
        notPaidTutorSalaryDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.NOT_PAID_TUTOR_SALARY_DAO_IMPL);
        hallReservationDAO = DaoFactory.getInstance().getDao(connection, DaoTypes.HALL_RESERVATION_DAO_IMPL);
        convertor = new Convertor();
    }

    @Override
    public boolean add(ClassesDTO classesDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(ClassesDTO classesDTO, HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException {
        System.out.println("in class service impl");
        try {
            connection.setAutoCommit(false);
            boolean isAdd = classesDAO.add(convertor.toClasses(classesDTO));
            System.out.println("add class " + isAdd);
            if (isAdd) {
                boolean isAddHallReservation = hallReservationDAO.add(convertor.toHallReservation(hallReservationDTO));
                System.out.println("add hallReservation " + isAddHallReservation);
                if (isAddHallReservation) {
                    String date = String.valueOf(LocalDate.now());
                    int year = Integer.parseInt(date.split("-")[0]);
                    NotPaidTutorSalaryDTO notPaidTutorSalary = new NotPaidTutorSalaryDTO(
                            classesDTO.getTutorId(),
                            classesDTO.getClassCode(),
                            year,
                            MonthName.getMonthName(date),
                            0.00
                    );
                    boolean isAddNotPaid = notPaidTutorSalaryDAO.add(convertor.toNotPaidTutorSalary(notPaidTutorSalary));
                    System.out.println("is add not paid tutor Salary " + isAddNotPaid);
                    if (isAddNotPaid) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(ClassesDTO classesDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ClassesDTO classesDTO, HallReservationDTO hallReservationDTO) throws SQLException, ClassNotFoundException {
        try {
            connection.setAutoCommit(false);
            boolean isUpdate = classesDAO.update(convertor.toClasses(classesDTO));
            if (isUpdate) {
                boolean isUpdateHallReservation = hallReservationDAO.update(convertor.toHallReservation(hallReservationDTO));
                if (isUpdateHallReservation) {
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
    public boolean delete(Object... args) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ClassesDTO searchById(Object... args) throws SQLException, ClassNotFoundException {
        return convertor.toClassesDTO(classesDAO.searchById(args));
    }

    @Override
    public List getAll() throws SQLException, ClassNotFoundException {
        List<Classes> list = classesDAO.getAll();
        List<ClassesDTO> list2 = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            list2.add(convertor.toClassesDTO(list.get(i)));
        }
        return list2;
    }

    @Override
    public boolean existByPk(Object... args) {
        return false;
    }

    @Override
    public String getLastId() throws SQLException, ClassNotFoundException {
        return classesDAO.getLastId();
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        String lastClassCode = getLastId();
        if (lastClassCode == null) {
            return "C0001";
        } else {
            String[] split = lastClassCode.split("[C]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newClassCode = String.format("C%04d", lastDigits);
            return newClassCode;
        }
    }

    @Override
    public List<String> loadIdToComboBox() throws SQLException, ClassNotFoundException {
        List<String> list = classesDAO.loadIdToComboBox();
        return list;
    }

    @Override
    public List<String> loadGradeToComboBox() throws SQLException, ClassNotFoundException {
        List<String> list = classesDAO.loadGradeToComboBox();
        return list;
    }

    @Override
    public List<ClassesDTO> getDataToScheduleTable(String dayName) throws SQLException, ClassNotFoundException {
        List<Classes> list = classesDAO.getDataToScheduleTable(dayName);
        List<ClassesDTO> list1= new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            list1.add(convertor.toClassesDTO(list.get(i)));
        }
        return list1;
    }
}
