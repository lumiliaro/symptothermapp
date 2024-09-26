import { Box, Center, Tabs } from "@mantine/core";
import SyTrackDayDatePicker from "../components/SyTrackDayDatePicker";
import CyclusView from "./forms/Cyclus/view";
import TrackDayMainView from "./forms/TrackDay/main";
import ChangelogView from "./forms/changelog/view";

export default function MainView() {
    return (
        <Tabs defaultValue="track" orientation="horizontal" keepMounted={false}>
            <Tabs.List>
                <Tabs.Tab value="track">Erfassung</Tabs.Tab>
                <Tabs.Tab value="cyclus">Zyklus</Tabs.Tab>
                <Tabs.Tab value="changelog">Changelog</Tabs.Tab>
            </Tabs.List>
            <Tabs.Panel value="track">
                <Box mt="lg" mb="lg">
                    <>
                        <Center>
                            <SyTrackDayDatePicker />
                        </Center>
                        <TrackDayMainView />
                    </>
                </Box>
            </Tabs.Panel>
            <Tabs.Panel value="cyclus">
                <Box mt="lg" mb="lg">
                    <CyclusView />
                </Box>
            </Tabs.Panel>
            <Tabs.Panel value="changelog">
                <Box mt="lg" mb="lg">
                    <ChangelogView />
                </Box>
            </Tabs.Panel>
        </Tabs>
    );
}
