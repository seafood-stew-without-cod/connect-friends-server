package gdg.hack.connectfriendsserver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoaderBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final DataLoader dataLoader;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (dataLoader.isValid()) {
      dataLoader.loadData();
    }
  }
}
