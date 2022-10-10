package network.redbelly.sevm;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.model.Storage;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

public class SendContractTest {

        private static final Logger LOGGER = LoggerFactory.getLogger(SendContractTest.class);

        static final BigInteger GAS_PRICE = BigInteger.valueOf(0L);
        // static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L); // for
        // G
        static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
        static final StaticGasProvider STATIC_GAS_PROVIDER = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
        private static final String URL = "http://sevm1.testnet.redbelly.network:8545";
        // private static final String URL = "http://127.0.0.1:8545";
        private static final String PASSWORD = "667ec24609c97f1c063a2e7497b42365eb69699b7710a02322e4e791830dc4ad";

        // private static final String PASSWORD =
        // "19e3c6917074c1e75b492a689c03153c6dc6b883c9c6c1935861d5231b54149c";
        // private static final String URL = "http://127.0.0.1:7545";

        // static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
        // static final StaticGasProvider STATIC_GAS_PROVIDER =
        // new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
        @Test
        public void testBlock() throws InterruptedException, ExecutionException {
                Web3j web3j = Web3j.build(new HttpService(URL));

                EthBlockNumber result = new EthBlockNumber();
                result = web3j.ethBlockNumber()
                                .sendAsync()
                                .get();
                LOGGER.info("Number of Block {}", result.getBlockNumber());
        }

        @Test
        public void test() throws Exception {

                Web3j web3j = Web3j.build(new HttpService(URL));

                Credentials credentials = Credentials.create(PASSWORD);

                for (int i = 0; i < 10; i++) {
                        CompletableFuture<Storage> request = Storage
                                        .deploy(web3j, credentials, provider(i, GAS_LIMIT.intValue()))
                                        .sendAsync();
                        LOGGER.info("Sent Contract {}", i);
                        Storage storage = request.get();
                        Thread.sleep(500);
                }
                // EthGetTransactionCount count = web3j
                // .ethGetTransactionCount(credentials.getAddress(), new
                // DefaultBlockParameterNumber(0)).send();
                // LOGGER.info("{}", count.getTransactionCount());

                // EthBlock block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST,
                // true).send();
                // block.getBlock().getAuthor();
                // LOGGER.info("{}", block.getBlock().getAuthor());
        }

        private StaticGasProvider provider(int gasPrice, int gasLimit) {
                return new StaticGasProvider(BigInteger.valueOf(gasPrice), BigInteger.valueOf(gasLimit));
        }

}