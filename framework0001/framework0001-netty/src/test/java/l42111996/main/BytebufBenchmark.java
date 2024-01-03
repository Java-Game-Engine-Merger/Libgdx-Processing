package l42111996.main;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 *
 * Created by JinMiao
 * 2020/6/24.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Warmup(iterations=1)
@Measurement(iterations=3)
@State(Scope.Group)
public class BytebufBenchmark{

  private ByteBuf byteBuf;

  @Setup(Level.Trial)
  public void setUp() {
    byteBuf=ByteBufAllocator.DEFAULT.directBuffer(400);
    byteBuf.writeBytes(new byte[400]);
  }

  @Benchmark
  @Group("retainedDuplicate")
  public void retainedDuplicate() {
    byteBuf.retainedDuplicate().release();
  }

  @Benchmark
  @Group("retain")
  public void retain() {
    byteBuf.retain().release();

  }
}
