package lotto.dao;

import lotto.dto.LottoDto;
import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.WinningLotto;
import lotto.util.LottoParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WinningLottoDao {
    private static final String DELIMITER = ",";
    private static final WinningLottoDao INSTANCE = new WinningLottoDao();

    private WinningLottoDao() {

    }

    public static WinningLottoDao getInstance() {
        return INSTANCE;
    }

    public void add(final WinningLotto winningLotto, final Integer round) {
        Connection conn = DBManager.getConnection();
        LottoDto lotto = winningLotto.getWinningLotto();
        try {
            String query = "insert into winning_lotto(round, numbers, bonus_number) values (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, round);
            pstmt.setString(2, makeNumbersFormat(lotto.getNumbers()));
            pstmt.setInt(3, winningLotto.getBonusNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(conn);
        }
    }

    private String makeNumbersFormat(final List<String> numbers) {
        return String.join(DELIMITER, numbers);
    }

    public WinningLotto findByRound(final int round) {
        Connection conn = DBManager.getConnection();
        try {
            String query = "select numbers, bonus_number from winning_lotto where round = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, round);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Lotto lotto = new LottoParser().parseLotto(rs.getString(1));
                LottoNumber lottoNumber = new LottoParser().parseLottoNumber(rs.getInt(2));
                return WinningLotto.of(lotto, lottoNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(conn);
        }
        return null;
    }

    public void deleteAll() {
        Connection conn = DBManager.getConnection();
        try {
            String query = "delete from winning_lotto";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection(conn);
        }
    }
}