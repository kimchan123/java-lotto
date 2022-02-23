package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * - **구입 금액을 입력받는다.** ()
 *     - [ 예외 ] 구입 금액은 `1000원 단위` 로 나눠져야한다.
 *     - [ 예외 ] 문자가 아니어야 한다.
 *     - [ 예외 ] 빈 문자열이 아니어야 한다.
 *     - [ 예외 ] 구입 금액은 `10만원을 초과할 수 없다.`
 */
public class PaymentTest {

	// *     - [ 예외 ] 구입 금액은 `1000원 단위` 로 나눠져야한다.
	@Test
	void division() {
		//given, when
		assertThatThrownBy(() -> new Payment("2500"))
			.isInstanceOf(IllegalArgumentException.class);
		//then
	}

	@Test
	void division2() {
		//given, when
		assertThatCode(() -> new Payment("3000"))
			.doesNotThrowAnyException();
		//then
	}

	//  *     - [ 예외 ] 음수가 아니어야 한다.

	@Test
	void 음수인_경우() {
		//given, when
		assertThatThrownBy(() -> new Payment("-1000"))
			.isInstanceOf(IllegalArgumentException.class);
		//then
	}

	// *     - [ 예외 ] 문자가 아니어야 한다.
	@Test
	void 문자인_경우_성공() {
		assertThatCode(() -> new Payment("1000"))
			.doesNotThrowAnyException();
	}

	@Test
	void 문자인_경우_실패() {
		assertThatThrownBy(() -> new Payment("천만원"))
			.isInstanceOf(NumberFormatException.class);
	}

	// *     - [ 예외 ] 빈 문자열이 아니어야 한다.
	@Test
	void 빈문자열_실패() {
		assertThatThrownBy(() -> new Payment(""))
			.isInstanceOf(NumberFormatException.class);
	}

	// *     - [ 예외 ] 구입 금액은 `10만원을 초과할 수 없다.`
	@Test
	void 구입금액_10만원_초과_실패() {
		assertThatThrownBy(() -> new Payment("110000"))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void 로또_생성_횟수() {
		assertThat(new Payment("14000").calculateLottoCount()).isEqualTo(14);
	}

	//- **수익률을 계산한다**
	//     - (당첨금액 / 투입금액)
	@Test
	void 수익률_계산() {
		List<Rank> lottos = Arrays.asList(Rank.FIFTH, Rank.FOURTH);
		//when
		LottoResult lottoResult = new LottoResult(lottos);
		Payment payment = new Payment("10000");

		double profitRate = payment.calculateProfitRate(lottoResult.calculateTotalProfit());

		assertThat(profitRate).isEqualTo(5.5);
	}
}