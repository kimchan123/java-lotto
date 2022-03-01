package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static domain.LottoTest.createLottoNumbers;
import static org.assertj.core.api.Assertions.assertThat;

public class LottosTest {

    @DisplayName("랭크 리스트 생성")
    @Test
    void calculate_rank_success() {
        //given
        List<Lotto> lotto = Arrays.asList(new Lotto(createLottoNumbers(6, 5, 4, 3, 2, 1)),
                new Lotto(createLottoNumbers(11, 5, 4, 3, 2, 1)));
        Lotto winningLotto = new Lotto(createLottoNumbers(6, 5, 4, 3, 2, 1));
        LottoNumber bonusNumber = LottoNumber.valueOf(7);
        Lottos lottos = new Lottos(lotto);

        //when
        List<Rank> ranks = lottos.calculateRank(new WinningLotto(winningLotto, bonusNumber));

        //then
        assertThat(ranks).contains(Rank.FIRST, Rank.THIRD);
    }
}
