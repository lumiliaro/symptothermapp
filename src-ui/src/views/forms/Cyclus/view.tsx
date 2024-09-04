import { FormProvider, useForm } from "react-hook-form";
import SyCyclusChart from "../../../components/SyCyclusChart";
import SyCyclusStatisticSelect from "../../../components/SyCyclusStatisticSelect";

export default function CyclusView() {
    const form = useForm({
        defaultValues: {
            cyclus: null,
        },
        mode: "onChange",
    });

    return (
        <FormProvider {...form}>
            <form>
                <SyCyclusStatisticSelect />
                <SyCyclusChart />
            </form>
        </FormProvider>
    );
}
