package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {
    @DisplayName("일치하는 개수에 해당하는 순위를 반환")
    @ParameterizedTest
    @MethodSource("createCountAndRank")
    void returnRank(int count, Rank expected) {
        assertThat(Rank.of(count)).isEqualTo(expected);
    }

    private static Stream<Arguments> createCountAndRank() {
        return Stream.of(
                Arguments.of(5, Rank.THIRD),
                Arguments.of(6, Rank.FIRST),
                Arguments.of(3, Rank.FIFTH),
                Arguments.of(2, Rank.LOSE)
        );
    }

    @DisplayName("순위들의 당첨금액을 모두 더함")
    @Test
    void sumWinningMoney() {
        List<Rank> ranks = Arrays.asList(Rank.FIFTH, Rank.SECOND, Rank.FOURTH);

        Money actual = Rank.sumWinningMoney(ranks);
        Money expected = Money.of(30055000);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("해당하는 Rank의 개수를 반환")
    @Test
    void getContainingCount() {
        List<Rank> ranks = Arrays.asList(Rank.FOURTH, Rank.FIFTH, Rank.SECOND, Rank.FOURTH, Rank.FOURTH);

        int actual = Rank.FOURTH.getContainingCount(ranks);
        int expected = 3;

        assertThat(actual).isEqualTo(expected);
    }
}