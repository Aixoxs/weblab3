package se.ifmo.web.dao;

import lombok.Data;
import se.ifmo.web.model.Point;
import se.ifmo.web.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PointDao {

    public PointDao(){
    }

    public void save(Point point) {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(SQLPoint.INSERT.QUERY)){
            statement.setBoolean(1, point.isInside());
            statement.setDouble(2,point.getRadius());
            statement.setDouble(3,point.getXCoordinate());
            statement.setDouble(4,point.getYCoordinate());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Point> getAllPoints(){
        List<Point> pointList = new ArrayList<>();
        try(Connection connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQLPoint.GETALL.QUERY);
            while (resultSet.next()){
                Point point = new Point();
                point.setInside(resultSet.getBoolean("inside_area"));
                point.setRadius(resultSet.getDouble("radius"));
                point.setXCoordinate(resultSet.getDouble("x_coordinate"));
                point.setYCoordinate(resultSet.getDouble("y_coordinate"));
                point.setId(resultSet.getInt("id"));
                pointList.add(point);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pointList;
    }

    public void deleteAllPoints(){
        try(Connection connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLPoint.DELETEALL.QUERY);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    enum SQLPoint{
        INSERT("INSERT INTO points VALUES (DEFAULT, (?), (?), (?), (?))"),
        GETALL("SELECT * FROM points"),
        DELETEALL("DELETE FROM points");
        String QUERY;

        SQLPoint(String QUERY){
            this.QUERY = QUERY;
        }
    }
}
