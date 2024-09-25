import { FormState } from "react-hook-form";

export const isFormClean = (
    formState: FormState<any>,
    formType?: "create" | "edit"
) => {
    if (formType === "create") {
        return false;
    }

    return Object.keys(formState.dirtyFields).length === 0;
};
