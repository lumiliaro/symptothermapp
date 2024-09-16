import { AppShell, Container, Flex } from "@mantine/core";
import SyFooter from "./components/SyFooter";
import MainView from "./views/main";

export default function App() {
    return (
        <AppShell footer={{ height: "auto", offset: true }}>
            <AppShell.Main>
                <Container>
                    <MainView />
                    <Flex
                        mih={50}
                        gap="md"
                        justify="center"
                        align="flex-end"
                        direction="row"
                        wrap="wrap"
                    >
                        <SyFooter />
                    </Flex>
                </Container>
            </AppShell.Main>
        </AppShell>
    );
}
